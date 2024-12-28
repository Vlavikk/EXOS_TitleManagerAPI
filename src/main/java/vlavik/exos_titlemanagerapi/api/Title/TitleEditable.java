package vlavik.exos_titlemanagerapi.api.Title;

import vlavik.exos_titlemanagerapi.api.Title.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.Title.Enums.TitleType;

import java.util.List;

public interface TitleEditable {
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
    public <T> void send(TitleType type, T text, int time, IgnoredType... ignoredOtherType);

    /**
     * Принудительно отправляет игроку, игнорируя очередь. После, очередь продолжит с того же момента, что и остановилась
     * @param text Параметры можно посмотреть в комментарии к <b>{@link #send}
     */
    public <T> void forcedSend(TitleType type, T text, int time, IgnoredType... ignoredOtherType);

    /**
     * Добавляет в очередь
     * @param numberInQueue номер позиции в очереди, смещает позиции(Минимальный индекс 1, в 0 индекс не добавлять);
     * @param text Параметры можно посмотреть в комментарии к <b>{@link #send}</b>
     *             <p>
     */
    public <T> void addQueue(TitleType type, T text, int time, int numberInQueue, IgnoredType... ignoredOtherType);

    /**
     * Очевидно, индексы начинаются с 0
     */
    public void removeQueue(TitleType type, int numberInQueue);

    /**
     *Принудительно обрывает текущую задачу/и.
     * @param cancelAll <p>
     *            - true обрывает все задачи<p>
     *            - false обрывает текущую задачу
     *                  <p>
     * @param type отмененные типы(Обязательно хотя бы один)
     */
    public void cancel(boolean cancelAll, TitleType... type);

    /**
     * Добавляет анимацию в очередь
     * @param type Параметры можно посмотреть в комментарии к <b>{@link #send}</b>
     *             <p>
     * @param animationFrame кадры анимации, List(String/Component) в противном случае {@link IllegalArgumentException}
     *                       <p>
     * @param delay Задержка между кадрами
     */
    public void sendAnimation(TitleType type, List<Object> animationFrame, int time, int delay, IgnoredType... ignoredOtherType);

    /**
     * Принудительно отправляет игроку, игнорируя очередь. После очередь продолжится с того же момента, что и остановилась
     * @param type Параметры можно посмотреть в комментарии к <b>{@link #sendAnimation}
     */
    public void forcedSendAnimation(TitleType type, List<Object> animationFrame, int time, int delay, IgnoredType... ignoredOtherType);
}
