def coroutine_example(name):
    print("start coroutine ... name:", name)

    while True:
        x = yield name
        if x is None:
            return "Zarten"
        print("send:", x)


coro = coroutine_example("Zarten")

next(coro)

coro.send(6)

try:
    coro.send(None)
except StopIteration as e:
    print(e.value)
