

# cosmetic-hardcore
Adds a cosmetic hardcore mode to your Survival Minecraft server

Download: https://www.mediafire.com/file/rm8zrm57jq7np9e/CosmeticHardcore-1.0.jar/file

# Features:

## Hardcore status

When a player joins for the first time, they will be assigned the hardcore status. On death, this status is removed, along with a message being sent to chat, notifying everyone that this player has returned to being a normal player.

![img](./images/losinghardcore.png)

## Name change

When a player has hardcore status, their name will be light red. This means everyone will be able to see that they have never died before when they speak in chat, for example.

![img](./images/hardcoreexample.png)

## Playerdata.yml

This file will save all of your player's hardcore status. The file layout is as follows:

```yaml
players:
  9c3918a4-08f4-40b4-90e9-88e347ef4566:
    hardcore: true
    playername: muffintwt
```

Where you can set "true" if a person is in hardcore, and "false" if they are not.

This also means that you will be able to update it whenever you want to. After you have updated the file (and saved it), type one of these three commands. They will reload the file.

```
/hardcorereload || /hcreload || /hcrl
```

