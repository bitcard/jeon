package geek.ma1uta.matrix;

public class RoomAlias extends Id {

    public static final char SIGIL = '#';

    protected RoomAlias(String localpart, String domain) {
        super(localpart, domain);
    }

    @Override
    public char sigil() {
        return SIGIL;
    }
}