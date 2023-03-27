import jsonschema.exceptions
from jsonschema import validate


class Validator:
    @staticmethod
    def Validate(data, scheme):
        try:
            validate(instance=data, schema=scheme)
        except jsonschema.exceptions.ValidationError as err:
            return False
        return True
