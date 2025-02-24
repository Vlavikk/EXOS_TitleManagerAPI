package vlavik.exos_titlemanagerapi.api.TitleManager;

import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass.AbstractTitle;

import java.util.List;

public interface TitleEditable {
    /**
     * Если создавать тайтл объектом, то его необходимо отправить через этот метод (если отправлять только методами снизу, то можно не беспокоиться)
     * @param title Тайтлы, размешать в порядке отправки(первый отправиться раньше чем второй "очевидно")
     */

    void send(AbstractTitle... title);
    /**
     * Добавляет в конец очереди. Каждый тип имеет отдельную очередь <p>
     *
     * @param type место выводимого текста <u>{@code Title}</u>, <u>{@code Actionbar}</u>, <u>{@code BossBar}</u>...
     *             <p>
     * @param text String/Component в противном случае {@link IllegalArgumentException}
     *             <p>
     * @param time время отведенное на воспроизведение текста(можно писать свое) в <u>тиках</u>:<p>
     *            - "-1" для бесконечного времени<p>
     *             <p>
     * @param ignoredOtherType если: <p>
     *                        - при пустом поле игнорируется(Ничего не отменяет)<p>
     *                        - <u>DELETE</u> отменит текущие задачи помимо выбранного type безвозвратно<p>
     *                        - <u>SAVE</u> отменит текущие задачи помимо выбранного type, после окончании данной задачи
     *                         продолжит отмененные задачи на том же времени
     *
     */
    <T> void send(TitleType type, T text, int time, IgnoredType... ignoredOtherType);

    /**
     * Принудительно отправляет игроку, игнорируя очередь. После, очередь продолжит с того же момента, что и остановилась
     * @param text Параметры можно посмотреть в комментарии к <b>{@link #send}
     */
    <T> void forceSend(TitleType type, T text, int time, IgnoredType... ignoredOtherType);

    /**
     * Добавляет анимацию в очередь
     * @param type Параметры можно посмотреть в комментарии к <b>{@link #send}</b>
     *             <p>
     * @param animationFrame кадры анимации, List(String/Component) в противном случае {@link IllegalArgumentException}
     *                       <p>
     *
     */
    void sendAnimation(TitleType type, List<?> animationFrame, int time, int delayBetweenFrame, IgnoredType... ignoredOtherType);

    /**
     * Принудительно отправляет игроку, игнорируя очередь. После очередь продолжится с того же момента, что и остановилась
     * @param type Параметры можно посмотреть в комментарии к <b>{@link #sendAnimation}
     */
    void forcedSendAnimation(TitleType type, List<?> animationFrame, int time, int delayBetweenFrame, IgnoredType... ignoredOtherType);

    <T> void sendActionBar(T text,int time,IgnoredType... ignoredOtherType);
    <T> void sendBossBar(T text,int time,IgnoredType... ignoredOtherType);
    <T> void sendTitle(T text,int time,IgnoredType... ignoredOtherType);
    <T> void sendTitle(T text,int timeFadeIn,int time,int timeFadeOut,IgnoredType... ignoredOtherType);

    <T> void sendActionBar(T text,int time,boolean forced,IgnoredType... ignoredOtherType);
    <T> void sendBossBar(T text,int time,boolean forced,IgnoredType... ignoredOtherType);
    <T> void sendTitle(T text,int time,boolean forced,IgnoredType... ignoredOtherType);
    <T> void sendTitle(T text,int timeFadeIn,int time,int timeFadeOut,boolean forced,IgnoredType... ignoredOtherType);

    void sendAnimationActionBar(List<?> animationFrame,int time,int delayBetweenFrame,IgnoredType... ignoredOtherType);
    void sendAnimationBossBar(List<?> animationFrame,int time,int delayBetweenFrame,IgnoredType... ignoredOtherType);
    void sendAnimationTitle(List<?> animationFrame,int time,int delayBetweenFrame,IgnoredType... ignoredOtherType);

    void sendAnimationActionBar(List<?> animationFrame,int time,int delayBetweenFrame,boolean forced,IgnoredType... ignoredOtherType);
    void sendAnimationBossBar(List<?> animationFrame,int time,int delayBetweenFrame,boolean forced,IgnoredType... ignoredOtherType);
    void sendAnimationTitle(List<?> animationFrame,int time,int delayBetweenFrame,boolean forced,IgnoredType... ignoredOtherType);

    /**
     * Добавляет в очередь
     * @param numberInQueue номер позиции в очереди, смещает позиции(Минимальный индекс 1, в 0 индекс не добавлять);
     * Параметры можно посмотреть в комментарии к <b>{@link #send}</b>
     *             <p>
     */
    void addQueue(AbstractTitle title,int numberInQueue);

    /**
     * Очевидно, индексы начинаются с 0
     */
    void removeQueue(TitleType type, int numberInQueue);

    /**
     *Удаляет все задачи из списка выбранного типа, типо
     * @param type отмененные типы
     */
    void cancel(TitleType... type);
    /**
     *Отменяет текущею задачу и начинает следующую
     * @param type зип зип зип
     */
    void next(TitleType type);

}
