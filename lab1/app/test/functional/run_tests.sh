#!/usr/bin/env bash

SCRIPT_DIR="$( dirname -- "$( readlink -f -- "$0"; )";)"
PYTEST_EXEC_PATH="$SCRIPT_DIR/../../../pytest/__main__.py"
PROGRAM_TO_TEST_NAME="lab1"

python3 "$PYTEST_EXEC_PATH" "$SCRIPT_DIR/pytest_config.json" "$SCRIPT_DIR/../../cmake-build-debug/$PROGRAM_TO_TEST_NAME"