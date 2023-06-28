import java.util.ArrayList;
import java.util.PriorityQueue;

class Heap {
    PriorityQueue<Integer> heap;

    public Heap(int max_size) {
        heap = new PriorityQueue<>(max_size);
    }

    public void insert(int key) {
        heap.offer(key);
    }

    public void delete(int key) {
        heap.remove(key);
    }

    public int getMin() {
        return heap.peek();
    }
}

class Poker {
    private int city_size;
    public int[] money;
    private Heap heap;

    public Poker(int city_size, int[] players, int[] max_loss, int[] max_profit) {
        this.city_size = city_size;
        this.money = new int[city_size];
        this.heap = new Heap(4);

        initMoney();

        for (int i = 0; i < players.length; i++) {
            Enter(players[i], max_loss[i], max_profit[i]);
        }
    }

    public void initMoney() {
        for (int i = 0; i < city_size; i++) {
            money[i] = 100000;
        }
    }

    public ArrayList<Integer> Play(int[] players, int[] bids, int winnerIdx) {
        int winnerId = players[winnerIdx];
        int winnerBid = bids[winnerIdx];
        ArrayList<Integer> playersToLeave = new ArrayList<>();

        for (int i = 0; i < players.length; i++) {
            int playerId = players[i];
            int playerBid = bids[i];

            if (playerBid > winnerBid) {
                money[playerId] -= playerBid;
                if (money[playerId] <= 0) {
                    playersToLeave.add(playerId);
                    heap.delete(playerId);
                }
            }
        }

        return playersToLeave;
    }

    public void Enter(int player, int max_loss, int max_profit) {
        heap.insert(player);
    }

    public ArrayList<Integer> nextPlayersToGetOut() {
        ArrayList<Integer> playersToLeave = new ArrayList<>();
        int minScore = Integer.MAX_VALUE;

        for (int player : heap.heap) {
            int score = Math.min(max_profit[player] + money[player], money[player] + max_loss[player]);
            if (score < minScore) {
                minScore = score;
                playersToLeave.clear();
                playersToLeave.add(player);
            } else if (score == minScore) {
                playersToLeave.add(player);
            }
        }

        return playersToLeave;
    }

    public ArrayList<Integer> playersInArena() {
        ArrayList<Integer> players = new ArrayList<>(heap.heap);
        return players;
    }

    public ArrayList<Integer> maximumProfitablePlayers() {
        ArrayList<Integer> profitablePlayers = new ArrayList<>();

        for (int i = 0; i < city_size; i++) {
            if (money[i] > 100000) {
                profitablePlayers.add(i);
            }
        }

        return profitablePlayers;
    }

    public ArrayList<Integer> maximumLossPlayers() {
        ArrayList<Integer> lossPlayers = new ArrayList<>();

        for (int i = 0; i < city_size; i++) {
            if (money[i] < 100000) {
                lossPlayers.add(i);
            }
        }

        return lossPlayers;
    }
}
