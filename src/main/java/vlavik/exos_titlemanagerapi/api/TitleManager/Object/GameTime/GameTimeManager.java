package vlavik.exos_titlemanagerapi.api.TitleManager.Object.GameTime;

import com.github.retrooper.packetevents.event.PacketListener;
@Deprecated //НА ПАМЯТЬ
public class GameTimeManager implements PacketListener{
//    public static HashMap<String,AsyncGameTimePlayer> playersAsyncTime = new HashMap<>();
//    public static List<String> skipPaket = new ArrayList<>();
//    public static void sendTime(Player player, int startTime, int activeTime){
//        AsyncGameTimePlayer asyncGameTimePlayer = playersAsyncTime.getOrDefault(
//                player.getName(),
//                new AsyncGameTimePlayer(player));
//        asyncGameTimePlayer.setStartTime(startTime);
//        asyncGameTimePlayer.setActiveTime(activeTime);
//        asyncGameTimePlayer.send();
//        if (!skipPaket.contains(player.getName())) skipPaket.add(player.getName());
//        playersAsyncTime.put(player.getName(),asyncGameTimePlayer);
//    }
//
//    @Override
//    public void onPacketSend(PacketSendEvent event) {
//        Player player = event.getPlayer();
//        if (event.getPacketType() != PacketType.Play.Server.TIME_UPDATE) return;
//        if (player != null){
//            WrapperPlayServerTimeUpdate packet = new WrapperPlayServerTimeUpdate(event);
//            if (playersAsyncTime.containsKey(player.getName())) {
//                if (skipPaket.contains(player.getName()) && packet.isTickTime()){//если время в тиках значит пакет наш слоняра
//                     skipPaket.remove(player.getName());
//                }else event.setCancelled(true);
//            }
//            else {
//                TitlePlayer titlePlayer = TitlePlayer.getTitlePlayer(player);
//                Arrays.stream(TitleType.values()).forEach(type -> {
//                    titlePlayer.getCurrentTitle(type).flatMap(AbstractTitle::getGameTime).ifPresent(gameTime -> {
//                        if (packet.getWorldAge() >= gameTime.getStartTime()
//                        && packet.getWorldAge() <= gameTime.getStartTime() + gameTime.getActiveTime()) {
//                            packet.setWorldAge(gameTime.getStartTime() + gameTime.getActiveTime() + 10);
//                        }
//                    });
//                });
//            }
//        }
//    }
}
