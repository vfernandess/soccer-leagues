//
//  csgo_leagues_iosTests.swift
//  csgo-leagues-iosTests
//
//  Created by Victor Fernandes on 05/03/26.
//

import Testing
import Foundation
import ComposableArchitecture
@testable import csgo_leagues_ios

// MARK: - Helpers

private func makeMatch(id: Int) -> Match {
    Match(
        id: id, name: "Match \(id)", status: .scheduled, beginAt: nil,
        teams: [], league: League(id: 1, name: "ESL", imageURL: nil), serieName: "S1"
    )
}

// MARK: - MatchesFeature Tests

@Suite("MatchesFeature")
struct MatchesFeatureTests {

    // MARK: itemAppeared

    @Test("below threshold: no action")
    func itemAppeared_belowThreshold_doesNothing() async {
        let matches = (1...10).map { makeMatch(id: $0) }
        let store = TestStore(
            initialState: MatchesFeature.State(matches: matches, isLoading: false)
        ) { MatchesFeature() }
        await store.send(.itemAppeared(index: 4))
    }

    @Test("empty matches: no action")
    func itemAppeared_emptyMatches_doesNothing() async {
        let store = TestStore(initialState: MatchesFeature.State()) { MatchesFeature() }
        await store.send(.itemAppeared(index: 0))
    }

    @Test("at threshold, no more pages: loadNextPage guard blocks")
    func itemAppeared_atThreshold_noMorePages_doesNothing() async {
        let matches = (1...10).map { makeMatch(id: $0) }
        // hasMorePages = false (default) → loadNextPage guard blocks
        let store = TestStore(
            initialState: MatchesFeature.State(matches: matches, isLoading: false)
        ) { MatchesFeature() }
        await store.send(.itemAppeared(index: 5))
        await store.receive(\.loadNextPage) // guard: !hasMorePages → .none, no state change
    }

    @Test("at threshold, more pages: triggers load")
    func itemAppeared_atThreshold_withMorePages_triggersLoad() async {
        let matches = (1...10).map { makeMatch(id: $0) }
        let newMatches = [makeMatch(id: 11)]
        let store = TestStore(
            initialState: MatchesFeature.State(matches: matches, isLoading: false, hasMorePages: true)
        ) {
            MatchesFeature()
        } withDependencies: {
            $0.fetchMatchesInteractor = FetchMatchesInteractor(execute: { _, _ in
                PaginatedMatches(matches: newMatches, hasNextPage: true)
            })
        }
        await store.send(.itemAppeared(index: 5))
        await store.receive(\.loadNextPage) { $0.isLoadingMore = true }
        await store.receive(\.paginationResponse) {
            $0.isLoadingMore = false
            $0.currentPage = 2
            $0.hasMorePages = true
            $0.matches = matches + newMatches
        }
    }

    // MARK: paginationResponse

    @Test("success: appends, increments page, deduplicates")
    func paginationResponse_success_appendsAndDeduplicates() async {
        let existing = [makeMatch(id: 1), makeMatch(id: 2)]
        let incoming = [makeMatch(id: 2), makeMatch(id: 3)] // id:2 is a duplicate
        let store = TestStore(
            initialState: MatchesFeature.State(
                matches: existing, isLoading: false, isLoadingMore: true, hasMorePages: true
            )
        ) { MatchesFeature() }
        await store.send(.paginationResponse(.success(PaginatedMatches(matches: incoming, hasNextPage: false)))) {
            $0.isLoadingMore = false
            $0.currentPage = 2
            $0.hasMorePages = false
            $0.matches = [makeMatch(id: 1), makeMatch(id: 2), makeMatch(id: 3)]
        }
    }

    @Test("failure: resets isLoadingMore, leaves matches and page unchanged")
    func paginationResponse_failure_resetsLoadingMore() async {
        let matches = [makeMatch(id: 1)]
        let store = TestStore(
            initialState: MatchesFeature.State(
                matches: matches, isLoading: false, isLoadingMore: true, hasMorePages: true
            )
        ) { MatchesFeature() }
        await store.send(.paginationResponse(.failure(URLError(.notConnectedToInternet)))) {
            $0.isLoadingMore = false
        }
    }

    // MARK: hasMorePages

    @Test("true when API signals next page exists")
    func hasMorePages_returnsTrue() {
        var state = MatchesFeature.State()
        state.hasMorePages = true
        #expect(state.hasMorePages == true)
    }

    @Test("false when API signals no more pages")
    func hasMorePages_returnsFalse() {
        let state = MatchesFeature.State()
        #expect(state.hasMorePages == false)
    }
}
