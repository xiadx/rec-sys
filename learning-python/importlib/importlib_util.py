import importlib.util
import sys


# For illustrative purposes.
name = 'itertools'


if name in sys.modules:
    print(f"{name!r} already in sys.modules")


# For illustrative purposes.
import tokenize

file_path = tokenize.__file__
module_name = tokenize.__name__

print(file_path)
print(module_name)

spec = importlib.util.spec_from_file_location(module_name, file_path)
module = importlib.util.module_from_spec(spec)
sys.modules[module_name] = module
spec.loader.exec_module(module)
