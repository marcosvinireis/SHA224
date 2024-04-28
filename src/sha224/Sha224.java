package sha224;

public class Sha224 {
    public static void main(String[] args) {
        String texto = "marcos";
        String hash = calcularSHA224(texto);
        System.out.println("Hash SHA-224: " + hash);
    }

    public static String calcularSHA224(String texto) {
        try {
            // Inicialização dos valores iniciais (IVs)
            int[] h = {
                    0xc1059ed8, 0x367cd507, 0x3070dd17, 0xf70e5939,
                    0xffc00b31, 0x68581511, 0x64f98fa7, 0xbefa4fa4
            };

            // Preparação do bloco de mensagem
            byte[] mensagemBytes = texto.getBytes("UTF-8");
            int mensagemLength = mensagemBytes.length;
            int tamanhoBloco = 64; // Tamanho do bloco em bytes (512 bits)
            int tamanhoFinal = mensagemLength + 1 + 8; // Mensagem + 1 bit de padding + 8 bytes de comprimento

            // Adiciona o bit "1" seguido de zeros até o próximo múltiplo de 512 (64 bytes)
            byte[] bloco = new byte[tamanhoFinal + (tamanhoBloco - tamanhoFinal % tamanhoBloco)];
            System.arraycopy(mensagemBytes, 0, bloco, 0, mensagemLength);
            bloco[mensagemLength] = (byte) 0x80; // Bit "1" de padding

            // Adiciona o comprimento original da mensagem (em bits) como um inteiro de 64 bits no final do bloco
            long mensagemBits = (long) mensagemLength * 8;
            for (int i = 0; i < 8; i++) {
                bloco[bloco.length - 8 + i] = (byte) (mensagemBits >>> (56 - i * 8));
            }

            // Processamento do bloco de mensagem
            int[] w = new int[64];
            for (int j = 0; j < bloco.length; j += 64) {
                for (int i = 0; i < 16; i++) {
                    w[i] = (bloco[j + i * 4] & 0xff) << 24 |
                            (bloco[j + i * 4 + 1] & 0xff) << 16 |
                            (bloco[j + i * 4 + 2] & 0xff) << 8 |
                            (bloco[j + i * 4 + 3] & 0xff);
                }

                for (int i = 16; i < 64; i++) {
                    int s0 = rightRotate(w[i - 15], 7) ^ rightRotate(w[i - 15], 18) ^ (w[i - 15] >>> 3);
                    int s1 = rightRotate(w[i - 2], 17) ^ rightRotate(w[i - 2], 19) ^ (w[i - 2] >>> 10);
                    w[i] = w[i - 16] + s0 + w[i - 7] + s1;
                }

                int a = h[0], b = h[1], c = h[2], d = h[3], e = h[4], f = h[5], g = h[6], h0 = h[7];

                for (int i = 0; i < 64; i++) {
                    int S1 = rightRotate(e, 6) ^ rightRotate(e, 11) ^ rightRotate(e, 25);
                    int ch = (e & f) ^ (~e & g);
                    int temp1 = h0 + S1 + ch + k[i] + w[i];
                    int S0 = rightRotate(a, 2) ^ rightRotate(a, 13) ^ rightRotate(a, 22);
                    int maj = (a & b) ^ (a & c) ^ (b & c);
                    int temp2 = S0 + maj;

                    h0 = g;
                    g = f;
                    f = e;
                    e = d + temp1;
                    d = c;
                    c = b;
                    b = a;
                    a = temp1 + temp2;
                }

                h[0] += a;
                h[1] += b;
                h[2] += c;
                h[3] += d;
                h[4] += e;
                h[5] += f;
                h[6] += g;
                h[7] += h0;
            }

            // Formata o hash final como uma string hexadecimal
            StringBuilder hashBuilder = new StringBuilder();
            for (int i = 0; i < h.length - 1; i++) {
                hashBuilder.append(String.format("%08x", h[i]));
            }

            return hashBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int rightRotate(int x, int n) {
        return (x >>> n) | (x << (32 - n));
    }

    private static final int[] k = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5,
            0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3,
            0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc,
            0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7,
            0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13,
            0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3,
            0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5,
            0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208,
            0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };
}
