package geek.ma1uta.matrix;

public class EventId extends Id {

    public static final char SIGIL = '$';

    protected EventId(String localpart, String domain) {
        super(localpart, domain);
    }

    @Override
    public char sigil() {
        return SIGIL;
    }
}