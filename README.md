# Keystroke Exit

Keystroke Exit is a client-side Fabric mod that closes open container screens when
you press one of Minecraft's configured movement keys. Rebound layouts such as
ESDF work automatically.

Unlike the original implementation, it does not disable itself in creative mode.
It follows Minecraft's active focus chain and keeps the screen open whenever a
text box is focused. This preserves typing in:

- creative inventory search
- anvil item names
- recipe and knowledge-book search
- compatible text fields added by other mods

The key state is tracked continuously, so opening a container while already
holding a movement key does not immediately close it.

## Compatibility

- Minecraft 1.21 through 26.2
- Fabric Loader
- Windows and macOS (Linux should also work)
- Client side only

Stonecutter maintains dedicated build targets at API compatibility boundaries:
1.21.1, 1.21.4, 1.21.7, 1.21.10, 1.21.11, 26.1.2, and 26.2.

## Building

Java 25 or newer is recommended for the complete version matrix.

```bash
./gradlew buildAndCollect --max-workers=1
```

Collected jars are written under `build/libs/`.

## License

CC0-1.0, matching the original
[pheological/keystroke-exit](https://github.com/pheological/keystroke-exit).
