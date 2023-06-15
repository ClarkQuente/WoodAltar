<h1 align="center">WoodAltar</h1>

<p align="center">Crie altares para seus jogadores!</p>

<h3> • Como pegar um altar pelo ID </h3>

```java
Altar altar = WoodAltarAPI.getInstance().getAltar(id);
```

<h3> • Como pegar o evento de dano ao altar </h3>

```java
@EventHandler
public void onDamage(AltarDamageEvent e) {
  Player player = e.getPlayer();
  Altar altar = e.getAltar();
  
  // TODO
}
```

<h3> • Como pegar o evento ao destruir o altar </h3>

```java
@EventHandler
public void onDestroy(AltarDestroyEvent e) {
  Player player = e.getPlayer();
  Altar altar = e.getAltar();
  
  // TODO
}
```
