### Как использовать?
# Пример
```java
TitlePlayer titlePlayer = TitlePlayer.getTitlePlayer(player);
titlePlayer.send(TitleEditable.Type.TITLE,"custom EXOS plugin!",20, ExCustomTitle.IgnoredType.SAVE);
```
Не создавайте класс TitlePlayer через
```java
new TitlePlayer();
```
Это повлечет неправильную работу плагина
### Добавление зависимости
```html
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```
```html
<dependency>
    <groupId>com.github.Vlavikk</groupId>
    <artifactId>EXOS_TitleManagerAPI</artifactId>
    <version>master</version>
</dependency>
```
