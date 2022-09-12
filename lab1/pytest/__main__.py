import sys

from tester import run_tests, FailedTest, PassedTest
from args import parse_exec_args


def main():
    args = parse_exec_args(sys.argv)
    if args is None:
        print('Error: wrong amount of arguments. Usage: python3 main.py <config_path> <executable_path>')
        exit(1)

    completed_tests = run_tests(args.configPath, args.execPath)
    for completed_test in completed_tests:
        if isinstance(completed_test, PassedTest):
            print('Test passed', '"' + completed_test.testName + '"')
        elif isinstance(completed_test, FailedTest):
            print('Test failed')
            print('\tName: "' + completed_test.testName + '"')
            print('\tError: "' + completed_test.errorMsg + '"')
            print('\tReturn code: ' + str(completed_test.returnCode))


if __name__ == '__main__':
    main()
