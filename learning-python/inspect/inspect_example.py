import inspect
from inspect import signature


def foo(a, *, b:int, **kwargs):
    pass


def test(a, *, b):
    print(a)
    print(b)


def main():
    self = __import__(__name__)
    print(inspect.getmembers(self))
    print(inspect.getmodulename("/Users/xdx/anaconda3/lib/python3.7/inspect.py"))
    print(inspect.getmodule(self))
    print(inspect.ismodule(self))
    print(inspect.isclass(self))
    print(inspect.ismethod(self))

    sig = signature(foo)

    print(str(sig))
    print(str(sig.parameters['b']))
    print(sig.parameters['b'].annotation)

    sig = signature(test)
    ba = sig.bind(10, b=20)
    test(*ba.args, **ba.kwargs)

    func = getattr(self, "test")
    ba = sig.bind(1, b=2)
    func(*ba.args, **ba.kwargs)


if __name__ == "__main__":
    main()
