import java.util.stream.IntStream;

public class MonsterZoo {
    double distance = 0.0; // 歩いた距離
    int balls = 10; // モンスターを捕まえられるボールの数
    int fruits = 0; // ぶつけるとモンスターが捕まえやすくなるフルーツ

    // 卵は最大9個まで持てる．移動するたびに距離が加算される．
    double eggDistance[] = new double[9];
    boolean egg[] = new boolean[9];

    // ユーザがGetしたモンスター一覧
    String userMonster[] = new String[100];

    // モンスター図鑑
    String monsterZukan[] = new String[22];
    double monsterRare[] = new double[22];

    // 呼び出すと1km distanceが増える
    void move() {
        this.distance++;

        // 卵の距離更新
        IntStream.range(0, this.egg.length)
            .filter(i -> this.egg[i])
            .forEach(i -> this.eggDistance[i]++);

        // ズーstationまたはモンスターイベントの処理
        int flg1 = (int) (Math.random() * 10); // 0,1の場合はズーstation，7~9の場合はモンスター
        if (flg1 <= 1) {
            System.out.println("ズーstationを見つけた！");
            int b = (int) (Math.random() * 3); // ball
            int f = (int) (Math.random() * 2); // fruits
            int e = (int) (Math.random() * 2); // egg
            System.out.println("ボールを" + b + "個，フルーツを" + f + "個，卵を" + e + "個Getした！");
            this.balls += b;
            this.fruits += f;

            // 卵をセット
            if (e >= 1) {
                IntStream.range(0, this.eggDistance.length)
                    .filter(i -> !this.egg[i])
                    .findFirst()
                    .ifPresent(i -> {
                        this.egg[i] = true;
                        this.eggDistance[i] = 0.0;
                    });
            }
        } else if (flg1 >= 7) {
            int m = (int) (this.monsterZukan.length * Math.random());
            System.out.println(this.monsterZukan[m] + "が現れた！");
            for (int i = 0; i < 3 && this.balls > 0; i++) {
                int r = (int) (6 * Math.random()); // 0~5の乱数
                if (this.fruits > 0) {
                    System.out.println("フルーツを投げた！捕まえやすさが倍になる！");
                    this.fruits--;
                    r = r * 2;
                }
                System.out.println(this.monsterZukan[m] + "にボールを投げた");
                this.balls--;
                if (this.monsterRare[m] <= r) {
                    System.out.println(this.monsterZukan[m] + "を捕まえた！");
                    IntStream.range(0, userMonster.length)
                        .filter(j -> this.userMonster[j] == null)
                        .findFirst()
                        .ifPresent(j -> this.userMonster[j] = this.monsterZukan[m]);
                    break;
                } else {
                    System.out.println(this.monsterZukan[m] + "に逃げられた！");
                }
            }
        }

        // 孵化チェック
        IntStream.range(0, this.egg.length)
            .filter(i -> this.egg[i] && this.eggDistance[i] >= 3)
            .forEach(i -> {
                System.out.println("卵が孵った！");
                int m = (int) (this.monsterZukan.length * Math.random());
                System.out.println(this.monsterZukan[m] + "が産まれた！");
                IntStream.range(0, userMonster.length)
                    .filter(j -> this.userMonster[j] == null)
                    .findFirst()
                    .ifPresent(j -> this.userMonster[j] = this.monsterZukan[m]);
                this.egg[i] = false;
                this.eggDistance[i] = 0.0;
            });
    }

    public double getDistance() {
        return distance;
    }

    public int getBalls() {
        return balls;
    }

    public int getFruits() {
        return fruits;
    }

    public String[] getUserMonster() {
        return userMonster;
    }

    public void setMonsterZukan(String[] monsterZukan) {
        this.monsterZukan = monsterZukan;
    }

    public void setMonsterRare(double[] monsterRare) {
        this.monsterRare = monsterRare;
    }
}
