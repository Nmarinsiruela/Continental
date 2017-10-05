package nmarin.eite.com.continentalplus;

class Player implements Comparable<Player>{

    private String namePlayer;
    private int totalPoints;

    public Player(String namePlayer, int totalPoints){
        setNamePlayer(namePlayer);
        setTotalPoints(totalPoints);
    }

    public String getNombreJugador() {
        return namePlayer;
    }

    private void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }


    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int compareTo(Player player) {
        int compareQuantity = (player).getTotalPoints();

        //ascending order
        return this.totalPoints - compareQuantity;

        //descending order
        //return compareQuantity - this.quantity;
    }
}
