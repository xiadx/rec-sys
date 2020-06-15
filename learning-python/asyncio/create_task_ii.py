import asyncio


async def main():
    print("hello")
    await asyncio.sleep(1)
    print("world")


loop = asyncio.get_event_loop()
task = loop.create_task(main())
print(task)
loop.run_until_complete(task)
print(task)
loop.close()
