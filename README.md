### Как использовать?
```java
TitlePlayer titlePlayer = TitlePlayer.getTitlePlayer(player);
titlePlayer.send(TitleType.TITLE, "custom EXOS plugin!", 20, IgnoredType.SAVE);
titlePlayer.sendTitle("1",40);
```
```java
ExBossBar bossBar = new ExBossBar("1",19,IgnoredType.SAVE);
ExTitle title = new ExTitle("1",10,60,69);
ExActionBarAnimation actionBarAnim = new ExActionBarAnimation(List.of("1", "2", "3"), 39,20);
titlePlayer.send(bossBar,title,actionBarAnim);
```
Некоторые параметры не интуитивно понятны, поэтому рекомендую прочитать комментарии к методам в интерфейсе.<p>
При необходимости можно посмотреть реализацию методов в классе TitlePlayer.
## Внимание!
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
    <version>1.2.6</version>
</dependency>
```
