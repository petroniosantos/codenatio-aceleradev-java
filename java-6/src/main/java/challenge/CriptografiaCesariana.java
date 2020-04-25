package challenge;

import java.util.stream.Stream;

public class CriptografiaCesariana implements Criptografia {
    public static final int CHAR_CODE_A = (int) 'a';
    public static final int CHAR_CODE_Z = (int) 'z';
    public static final int RANG_Z_TO_A = CHAR_CODE_Z - CHAR_CODE_A + 1;
    public static final int CYPHER = 3;

    @Override
    public String criptografar(String texto) {
        if (texto.isEmpty() || texto == null) throw new IllegalArgumentException();
        return criptoCesar(texto, true);
    }

    @Override
    public String descriptografar(String texto) {
        if (texto.isEmpty() || texto == null) throw new IllegalArgumentException();
        return criptoCesar(texto, false);
    }

    private static String criptoCesar(String text, boolean cryptography) {
        Stream<Character> stream = text.toLowerCase().chars().mapToObj(i -> (char) i);

        return stream.map(ascii -> {
            if (ascii < CHAR_CODE_A || ascii > CHAR_CODE_Z) {
                return String.valueOf(ascii);
            }
            ascii = (char) (cryptography ? ascii + CYPHER : ascii - CYPHER);
            ascii = processOutOfBounds(ascii);
            return String.valueOf(ascii);
        }).reduce("", (texto, ascii) -> texto.concat(ascii));
    }

    private static char processOutOfBounds(char ascii) {
        if (ascii < CHAR_CODE_A) return (char) (ascii + RANG_Z_TO_A);
        if (ascii > CHAR_CODE_Z) return (char) (ascii - RANG_Z_TO_A);
        return ascii;
    }
}
