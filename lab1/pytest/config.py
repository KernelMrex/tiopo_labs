import json
from typing import Union, Dict

from error.config import *
from type.config import *


def _map_stream_config_params(data: Dict[str, str]) -> FileStreamParams:
    return FileStreamParams(path=data['path'])


def _map_case_params(data: Dict[str, Union[str, Dict[str, str], int, List[str]]]) -> CaseParams:
    input_params = _map_stream_config_params(data['input']) if 'input' in data else None
    output_params = _map_stream_config_params(data['output']) if 'output' in data else None
    expected_params = _map_stream_config_params(data['expected']) if 'expected' in data else None

    return CaseParams(
        name=data['name'],
        description=data['description'],
        execArgs=data['exec_args'],
        input=input_params,
        output=output_params,
        expected=expected_params,
        returnCode=data['return_code']
    )


def parse_config_params_from_file(path: str) -> ConfigParams:
    try:
        json_file = open(path, 'r')
    except OSError:
        raise ConfigFileNotFoundError('Config file not found in path "' + path + '"')

    with json_file:
        parsed_data = json.load(json_file)

    mapped_cases_params = []
    try:
        parsed_cases_params = parsed_data['cases']
        for parsed_case_params in parsed_cases_params:
            mapped_cases_params.append(_map_case_params(parsed_case_params))
    except KeyError as err:
        raise MalformedConfigError('Cannot parse config. Key: ' + err.args[0] + ' Data: ' + parsed_data)

    return ConfigParams(mapped_cases_params)
