import filecmp
import os
from subprocess import run

from config import *
from type.tester import *


def _execute_test(exec_path: str, case_param: CaseParams) -> Union[PassedTest, FailedTest]:
    s_in = open(case_param.input.path, 'r') if case_param.input else None
    s_out = open(case_param.output.path, 'w') if case_param.output else None

    s_nowhere = open(os.devnull, 'w')

    exec_result = run(
        [exec_path, *case_param.execArgs],
        stdin=s_in,
        stdout=s_out if s_out else s_nowhere,
        stderr=s_out if s_out else s_nowhere,
    )

    if s_in and not s_in.closed:
        s_in.close()
    if s_out and not s_out.closed:
        s_out.close()

    if exec_result.returncode != case_param.returnCode:
        return FailedTest(
            testName=case_param.name,
            errorMsg='Return code does not match',
            returnCode=exec_result.returncode
        )

    if case_param.expected and case_param.output:
        if not filecmp.cmp(case_param.output.path, case_param.expected.path):
            return FailedTest(
                testName=case_param.name,
                errorMsg='Output is different with what expected',
                returnCode=exec_result.returncode
            )

    return PassedTest(case_param.name)


def run_tests(config_path: str, exec_path: str) -> List[Union[FailedTest, PassedTest]]:
    config_params = parse_config_params_from_file(config_path)

    completed_tests = []
    for case_params in config_params.caseParams:
        completed_tests.append(_execute_test(exec_path, case_params))

    return completed_tests
