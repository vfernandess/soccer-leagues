cmake_minimum_required(VERSION 3.22.1)
project("native-secrets")

set(SECRETS_FILE "${CMAKE_CURRENT_SOURCE_DIR}/secrets.cpp")

if(NOT EXISTS "${SECRETS_FILE}")
    message(FATAL_ERROR "Missing generated secrets.cpp. Run `python3 scripts/generate_secrets.py --platform android` from the repository root before building.")
endif()

add_library(native-secrets SHARED "${SECRETS_FILE}")

target_link_libraries(native-secrets log)
