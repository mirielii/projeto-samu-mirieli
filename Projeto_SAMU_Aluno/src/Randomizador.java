import java.util.Random;

/**
 * Fornece um grau de controle sobre os aspectos aleatórios da simulação.
 * Usando um gerador compartilhado com semente fixa, duas execuções com os
 * mesmos parâmetros produzem exatamente o mesmo resultado.
 */
public class Randomizador
{
    private static final int SEMENTE = 2026;
    private static final Random rand = new Random(SEMENTE);
    private static final boolean usarCompartilhado = true;

    /**
     * @return O gerador de números aleatórios da simulação.
     */
    public static Random getRandom()
    {
        if(usarCompartilhado) {
            return rand;
        }
        else {
            return new Random();
        }
    }

    /**
     * Reinicia o gerador compartilhado com a semente original.
     */
    public static void reinicia()
    {
        if(usarCompartilhado) {
            rand.setSeed(SEMENTE);
        }
    }
}
