import asyncio


async def main():
    print("hello")
    await asyncio.sleep(1)
    print("world")


loop = asyncio.get_event_loop()
loop.run_until_complete(main())
loop.close()
