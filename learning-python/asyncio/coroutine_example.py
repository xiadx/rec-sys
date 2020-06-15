def coroutine_example(name):
    print("start coroutine ... name:", name)
    x = yield name
    print("send:", x)


coro = coroutine_example("Zarten")

print("next:", next(coro))

coro.send(6)
