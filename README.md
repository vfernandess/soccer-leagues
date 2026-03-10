# CSTV — CS:GO Matches

A native iOS app to browse live and upcoming CS:GO matches using the PandaScore API.

## Overview

CSTV displays CS:GO matches with live status, team matchups, league information, and detailed player rosters. Matches are shown from the current date onward, sorted with live matches first.

## Architecture

Clean Architecture + TCA (The Composable Architecture)

### Layers
- **Presentation** — SwiftUI Views + TCA Features (Reducer, State, Action)
- **Domain** — Pure Swift Entities + Interactors + Repository Protocols
- **Data** — Moya Networking + DTOs + Mappers + Repository Implementations
- **Core** — Secret management, Design System, Extensions, DI

### Patterns
- Unidirectional data flow via TCA
- Repository pattern for data abstraction
- Dependency injection via TCA `@Dependency`

## Libraries

| Library | Purpose |
|---------|---------|
| [TCA (swift-composable-architecture)](https://github.com/pointfreeco/swift-composable-architecture) | State management, side effects, navigation |
| [Moya](https://github.com/Moya/Moya) | Type-safe HTTP networking abstraction over Alamofire |
| [Kingfisher](https://github.com/onevcat/Kingfisher) | Async image loading and caching |

## Setup & Run

1. **Clone the repository**
   ```bash
   git clone <repo-url>
   cd soccer-league
   ```

2. **Set up your API token**
   ```bash
   cp .env.example .env
   # Edit .env and fill in your PandaScore token:
   # PANDASCORE_TOKEN=your_token_here
   ```
   Get your token at [pandascore.co](https://pandascore.co)

3. **Open in Xcode**
   ```bash
   open soccer-league/soccer-league.xcodeproj
   ```

4. **Build & Run** — Select an iOS 17+ simulator and press Cmd+R

## Secret Management

API tokens are never hardcoded. The pipeline:

```
.env (git-ignored)
  → scripts/generate_secrets.py (Xcode pre-build phase)
  → Core/Secrets/secrets.c (git-ignored, XOR-obfuscated)
  → Swift via bridging header → get_api_token()
  → Moya Authorization: Bearer header
```

The token is XOR-obfuscated with a runtime-computed mask (LCG seed), so it never appears as plaintext in the compiled binary.

## Features

- Matches list with live/upcoming status
- Pull-to-refresh
- Infinite scroll pagination
- Match detail with player rosters (fetched in parallel)
- Figma-accurate design with design system tokens
- Portrait-only orientation

## Requirements

- Xcode 16+
- iOS 17.0+ deployment target
- Valid PandaScore API token in `.env`
