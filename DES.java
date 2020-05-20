import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DES {
    int[] leftShiftList = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    int[] PC1 = {
        57, 49, 41, 33, 25, 17, 9,
        1, 58, 50, 42, 34, 26, 18,
        10, 2, 59, 51, 43, 35, 27,
        19, 11, 3, 60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
        7, 62, 54, 46, 38, 30, 22,
        14, 6, 61, 53, 45, 37, 29,
        21, 13, 5, 28, 20, 12, 4
        };
    int[] PC2 = {
        14, 17, 11, 24, 1, 5,
        3, 28, 15, 6, 21, 10,
        23, 19, 12, 4, 26, 8,
        16, 7, 27, 20, 13, 2,
        41, 52, 31, 37, 47, 55,
        30, 40, 51, 45, 33, 48,
        44, 49, 39, 56, 34, 53,
        46, 42, 50, 36, 29, 32
        };
    int[] IP = {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7
        };   
    int[] FP = {
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25
        };   
    int[] E = {
        32, 1, 2, 3, 4, 5,
        4, 5, 6, 7, 8, 9,
        8, 9, 10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32, 1
        };   
    int[][] S1 = {
        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
        };
    int[][] S2 = {
        {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
        };
    int[][] S3 = {
        {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
        };
    int[][] S4 = {
        {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
        };
    int[][] S5 = {
        {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
        };
    int[][] S6 = {
        {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
        };
    int[][] S7 = {
        {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
        };
    int[][] S8 = {
        {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
        };
    int[] P = {
    		16, 7, 20, 21,
    		29, 12, 28, 17,
    		1, 15, 23, 26,
    		5, 18, 31, 10,
    		2, 8, 24, 14,
    		32, 27, 3, 9,
    		19, 13, 30, 6,
    		22, 11, 4, 25
        };
    int rightSpace, leftSpace;
    String[] keyArray = new String[16];
    String plainTextBinary = "";
    
    public static void main(String[] args) {
    	DES des = new DES();
    	
    	Map textResult = des.getText();
    	String text = textResult.get("text").toString();
    	String binaryText = textResult.get("binaryText").toString();
    	
    	String key = des.getKeys();
    	
    	Map cipherResult = des.encrypt(binaryText);
    	String encryptedText = cipherResult.get("encryptedText").toString();
    	String encryptedBinaryText = cipherResult.get("encryptedBinaryText").toString();
    	
    	Map decipherResult = des.decrypt(encryptedBinaryText);
    	String decryptedText = decipherResult.get("decryptedText").toString();
    	String decryptedBinaryText = decipherResult.get("decryptedBinaryText").toString();
    	
    	des.showResults(text, key, encryptedText, encryptedBinaryText, decryptedText, decryptedBinaryText);
    }
    
    public void showResults(String text, String key, String encryptedText, String encryptedBinaryText, String decryptedText, String decryptedBinaryText) {
    	System.out.println("\nText: " + text);
    	System.out.println("Key: " + key);
    	System.out.println("Encrypted Text: " + encryptedText);
    	System.out.println("Encrypted Binary Text: " + encryptedBinaryText.replaceAll("(.{8})(?!$)", "$1 "));
    	System.out.println("Decrypted Text: " + decryptedText);
    	System.out.println("Decrypted Binary Text: " + decryptedBinaryText.replaceAll("(.{8})(?!$)", "$1 "));
    }
    
    public String stringToBinary( String str ) {
        byte[] bytes = str.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                  binary.append((val & 128) == 0 ? 0 : 1);
                  val <<= 1;
            }
            binary.append(' ');
        }
        return binary.toString();
    }
    
    public String integerToString( String stream, int size ) { 
        String result = "";
        for (int i = 0; i < stream.length(); i += size) {
            result += (stream.substring(i, Math.min(stream.length(), i + size)) + " ");
        }  
        String[] ss = result.split( " " );
        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < ss.length; i++ ) { 
            sb.append( (char)Integer.parseInt( ss[i], 2 ) );                                                                                                                                                        
        }   
        return sb.toString();
    }  

    public String leftShift(String s, int k) { 
        String result = s.substring(k);
        for (int i = 0; i < k; i++) {
            result += s.charAt(i);
        }
        return result;
    }

    public Map<String, String> getText() {
    	String text;
    	do {
    	System.out.println("Enter Text: ");
        text = new Scanner(System.in).nextLine();
        // Verification
        if (text.length() < 8)
        	System.out.println("Text must be atleast 64-bits! (8 characters)");
    	} while (text.length() < 8);
    	
        String binaryText = stringToBinary(text);
        System.out.println("Binary Text: " + binaryText);
        binaryText = binaryText.replace(" ", ""); // Remove spaces
        
        Map<String, String> result = new HashMap();
        result.put("text", text);
        result.put("binaryText", binaryText);
        
        return result;
    }
    
    public String getKeys() {
    	String Key;
    	do {
    	 System.out.println("\nEnter key: ");
         Key = new Scanner(System.in).nextLine();
         // Verification
         if (Key.length() < 8) 
        	 System.out.println("Key must be atleast 64-bits! (8 characters)");
    	} while (Key.length() < 8);
         
         String binaryKey = stringToBinary(Key);
         System.out.println("Binary Key: " + binaryKey);
         binaryKey = binaryKey.replace(" ", ""); // Remove spaces
        
         // Calculate permutation using PC-1
         String permutatedKey = "";
         for (int i : PC1) {
             permutatedKey += binaryKey.charAt(i - 1);
         }
         permutatedKey.replaceAll("(.{8})(?!$)", "$1 ");
         String LK = permutatedKey.substring(0, 28); // 28 bits
         String RK = permutatedKey.substring(28, 56); // 28 bits

         // Do 16 left circular shifts for LK and RK
         String[] Keys = new String[16];
         String Lkey = LK;
         String Rkey = RK;
         int Index = 0;
         for (int a : leftShiftList) {
             Lkey = leftShift(Lkey, a);
             Rkey = leftShift(Rkey, a);
             Keys[Index] = Lkey + Rkey;
             Index++;
         }

         // Build 16 48-bit sub keys using the PC-2 Permutation table
         String RoundKey = "";
         Index = 0;
         for (String key : Keys) {
             for (int j : PC2) {
                 RoundKey += key.charAt(j - 1);
             }
             keyArray[Index] = RoundKey;
             Index++;
             RoundKey = "";
         }
         
         return Key;
    }
    
    public Map<String,String> encrypt(String binaryText) {
    	String encipher = "";
    	String finalResult = "";
    	String textEncipher = "";
    	try {
        	System.out.println("\n****************************** Encryption Started ******************************");
        	int plainTextLength = binaryText.length();
        	int leftpadString = plainTextLength % 64;
            int loop = leftpadString != 0 ? plainTextLength/64 + 1 : plainTextLength/64;
            int start = 0;
            int end = 64;
            for(int id = loop, wordCount = 1; id >0; id--, wordCount++) {
            	int leftpad = 64 - leftpadString;
                leftSpace = leftpad % 64 != 0 ? leftpad/8 : 0;
                end = plainTextLength - start < 64 ? plainTextLength : end;
                plainTextBinary = binaryText.substring(start, end);
                while(plainTextBinary.length() != 64) {
                	plainTextBinary = "0" + plainTextBinary;
                	} 
                String IPBinary = "";
                for (int i : IP) {
                	IPBinary += plainTextBinary.charAt(i - 1);
                	}

                // Convert permuted block into two 32 bits halves
                String LeftIPBinary = IPBinary.substring(0, 32);
                String RightIPBinary = IPBinary.substring(32, 64);

                // Proceed through 16 Rounds using a function f
                int counter = 1;
                for (String k : keyArray) {
                	System.out.println("64-bit " + wordCount+ " Encryption Round " + counter);
                	System.out.println("Key: " + k);

                	// Switch blocks
                	String LeftBlock = RightIPBinary;
                	System.out.println("Left Block: " + LeftBlock);
                	String expand = "";
                	for (int i : E) {
                		expand += RightIPBinary.charAt(i - 1);
                		}

                	// XOR 
                	StringBuilder sb = new StringBuilder();
                	for (int i = 0; i < k.length(); i++) {
                		sb.append((k.charAt(i) ^ expand.charAt(i)));
                		}
                	String result = sb.toString();         
                	String RB1 = result.substring(0, 6);
                	String row1 = String.valueOf(RB1.substring(0, 1) + RB1.substring(5, 6));
                	String col1 = String.valueOf(RB1.substring(1, 5));
                	int target = S1[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                	String binaryTarget = String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');
                	
                    String RB2 = result.substring(6, 12);
                    row1 = String.valueOf(RB2.substring(0, 1) + RB2.substring(5, 6));
                    col1 = String.valueOf(RB2.substring(1, 5));
                    target = S2[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                    binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                    String RB3 = result.substring(12, 18);
                    row1 = String.valueOf(RB3.substring(0, 1) + RB3.substring(5, 6));
                    col1 = String.valueOf(RB3.substring(1, 5));
                    target = S3[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                    binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                    String RB4 = result.substring(18, 24);
                    row1 = String.valueOf(RB4.substring(0, 1) + RB4.substring(5, 6));
                    col1 = String.valueOf(RB4.substring(1, 5));
                    target = S4[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                    binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                    String RB5 = result.substring(24, 30);
                    row1 = String.valueOf(RB5.substring(0, 1) + RB5.substring(5, 6));
                    col1 = String.valueOf(RB5.substring(1, 5));
                    target = S5[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                    binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                    String RB6 = result.substring(30, 36);
                    row1 = String.valueOf(RB6.substring(0, 1) + RB6.substring(5, 6));
                    col1 = String.valueOf(RB6.substring(1, 5));
                    target = S6[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                    binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                    String RB7 = result.substring(36, 42);
                    row1 = String.valueOf(RB7.substring(0, 1) + RB7.substring(5, 6));
                    col1 = String.valueOf(RB7.substring(1, 5));
                    target = S7[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                    binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                    String RB8 = result.substring(42, 48);
                    row1 = String.valueOf(RB8.substring(0, 1) + RB8.substring(5, 6));
                    col1 = String.valueOf(RB8.substring(1, 5));
                    target = S8[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                    binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                    // Permutate the output of the S-box(binaryTarget) using table P to obtain the final value 
                    String function = "";
                    for (int d : P) {
                    	function += binaryTarget.charAt(d - 1);
                    	}

                    // XOR Previous Left block with the function value
                    sb = new StringBuilder();
                    for (int i = 0; i < LeftIPBinary.length(); i++) {
                    	sb.append((LeftIPBinary.charAt(i) ^ function.charAt(i)));
                    	}
                    result = sb.toString();

                    RightIPBinary = result;
                    System.out.println("Right Block: " + RightIPBinary + "\n");
                    result = "";
                    counter++;
                    if (counter > 16) {
                    	result = RightIPBinary + LeftBlock;// Combine the two blocks
                    	finalResult = "";
                    	// Inverse the Initial permutation IP
                    	for (int x : FP) {
                    		finalResult += result.charAt(x - 1);
                    		}
                    	encipher += finalResult;
                    	textEncipher += id == 1 && leftSpace != 0 ? integerToString(finalResult, 8).substring(leftSpace) : integerToString(finalResult, 8);  
                    	}
                    LeftIPBinary = LeftBlock;
                    }
                end += 64;
                start += 64;
                System.out.println("Cipher 64-bit " + wordCount +": " + integerToString(finalResult, 8));
                System.out.println("_________________________________________________________________________________");
                }
            } catch (Exception e) {
            	e.printStackTrace();
            	}
    	System.out.println("\n****************************** Encryption Finished ******************************");
    	Map<String,String> result = new HashMap< String,String>(); 
    	result.put("encryptedText", textEncipher);
    	result.put("encryptedBinaryText", encipher);
    	
    	return result;
    	}
    
    public Map<String,String> decrypt (String encryptedBinaryText) {
    	System.out.println("\n****************************** Decryption Started ******************************");
    	String decipher = "";
    	String binaryDecipher = "";
    	int encipherLength = encryptedBinaryText.length();
        int leftpadString = encipherLength % 64;
        int loop = leftpadString != 0 ? encipherLength/64 + 1 : encipherLength/64;
        int start = 0;
        int end = 64;
        for(int id = loop, wordCount = 1; id >0; id--,wordCount++) {
            int leftpad = 64 - leftpadString;
            end = encipherLength - start < 64 ? encipherLength : end;
            String cipherTextBinary = encryptedBinaryText.substring(start, end);
            String IPBinary = "";
            for (int i : IP) {
                IPBinary += cipherTextBinary.charAt(i - 1);
            }

            // Convert permuted block into two 32 bits halves
            String LeftIPBinary = IPBinary.substring(0, 32);
            String RightIPBinary = IPBinary.substring(32, 64);

            // Proceed through 16 Rounds using a function f
            //Go through the 16 keys in the REVERSE ORDER
            int counter = 1;
            String k;
            for (int p = 15; p >= 0; p--) {
                System.out.println("64-bit " + wordCount+ " Decryption Round " + counter);
                k = keyArray[p];
                System.out.println("Key: " + k);

                // Switch blocks
                String LeftBlock = RightIPBinary;
                System.out.println("Left Block: " + LeftBlock);
                String expand = "";
                for (int i : E) {
                    expand += RightIPBinary.charAt(i - 1);
                }

                // XOR 
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < k.length(); i++) {
                    sb.append((k.charAt(i) ^ expand.charAt(i)));
                }
                String result = sb.toString();         

                String RB1 = result.substring(0, 6);
                String row1 = String.valueOf(RB1.substring(0, 1) + RB1.substring(5, 6));
                String col1 = String.valueOf(RB1.substring(1, 5));
                int target = S1[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                String binaryTarget = String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB2 = result.substring(6, 12);
                row1 = String.valueOf(RB2.substring(0, 1) + RB2.substring(5, 6));
                col1 = String.valueOf(RB2.substring(1, 5));
                target = S2[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB3 = result.substring(12, 18);
                row1 = String.valueOf(RB3.substring(0, 1) + RB3.substring(5, 6));
                col1 = String.valueOf(RB3.substring(1, 5));
                target = S3[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB4 = result.substring(18, 24);
                row1 = String.valueOf(RB4.substring(0, 1) + RB4.substring(5, 6));
                col1 = String.valueOf(RB4.substring(1, 5));
                target = S4[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB5 = result.substring(24, 30);
                row1 = String.valueOf(RB5.substring(0, 1) + RB5.substring(5, 6));
                col1 = String.valueOf(RB5.substring(1, 5));
                target = S5[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB6 = result.substring(30, 36);
                row1 = String.valueOf(RB6.substring(0, 1) + RB6.substring(5, 6));
                col1 = String.valueOf(RB6.substring(1, 5));
                target = S6[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB7 = result.substring(36, 42);
                row1 = String.valueOf(RB7.substring(0, 1) + RB7.substring(5, 6));
                col1 = String.valueOf(RB7.substring(1, 5));
                target = S7[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                String RB8 = result.substring(42, 48);
                row1 = String.valueOf(RB8.substring(0, 1) + RB8.substring(5, 6));
                col1 = String.valueOf(RB8.substring(1, 5));
                target = S8[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
                binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

                // Permutate the output of the S-box using table P 
                String function = "";
                for (int d : P) {
                    function += binaryTarget.charAt(d - 1);
                }

                // XOR Previous Left block with the function value
                sb = new StringBuilder();
                for (int i = 0; i < LeftIPBinary.length(); i++) {
                    sb.append((LeftIPBinary.charAt(i) ^ function.charAt(i)));
                }
                result = sb.toString();

                RightIPBinary = result;
                System.out.println("Right Block: " + RightIPBinary + "\n");
                result = "";
                counter++;
                if (counter > 16) {
                    result = RightIPBinary + LeftBlock;  //Reverse the order

                    // Inverse of the initial permutation
                    String finalResult = "";
                    for (int x : FP) {
                        finalResult += result.charAt(x - 1);
                    }
                    
                    binaryDecipher += finalResult;
                    decipher += id == 1 && leftSpace != 0 ? integerToString(finalResult, 8).substring(leftSpace) : integerToString(finalResult, 8);   
                    System.out.println("Decyphered 64-bit " + wordCount +" = " + integerToString(finalResult, 8));                     
                }
                LeftIPBinary = LeftBlock;
            }    
            end += 64;
            start += 64;
            System.out.println("_________________________________________________________________________________");
        }
        
        System.out.println("\n****************************** Decryption Finished ******************************");
        Map<String,String> result = new HashMap< String,String>(); 
    	result.put("decryptedText", decipher);
    	result.put("decryptedBinaryText", binaryDecipher);
    	
    	return result;     
    }
    
}