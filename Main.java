class Main {
  public static void main(String[] args) {
    (new Main()).init();
  }
  void print(Object o){ System.out.println(o);}
  void printt(Object o){ System.out.print(o);}

  void init(){
	
	char[] sub3 = new char[2];
	sub3[0] = 'e';
	sub3[1] = 'a';
	
	char[] sub4 = new char[2];
	sub4[0] = '\uA3D7';
	sub4[1] = '\uA3E8';
	 
	 
	// Original Text
	String originalText = Input.readFile("Original.txt");
	
	// Cipher by rotation 90 degrees
	String encodedMsg1 = rotate90(originalText);
	Input.writeFile("Encode1.txt", encodedMsg1);
	
	// reverseShiftCipher
	String encodedMsg2 = reverseShiftCipher(encodedMsg1);
	Input.writeFile("Encode2.txt", encodedMsg2);
	
	// SkipBy5Cipher
	String encodedMsg3 = skipByFiveCipher(encodedMsg2);
	Input.writeFile("Encode3.txt", encodedMsg3);
	
	// Pad/Add filler X for every 5 characters
	String encodedMsg4 = addFillerCharacter(encodedMsg3);
	Input.writeFile("Encode4.txt", encodedMsg4);
	
	// Cut By Thirds
	String encodedMsg5 = cutByThirds(encodedMsg4);
	Input.writeFile("Encode5.txt", encodedMsg5);
	
	// Swap every two characters
	String encodedMsg6 = swapEveryTwo(encodedMsg5);
	Input.writeFile("Encode6.txt", encodedMsg6);
	
	// Swap E and A with unicodes
	String encodedMsg7 = subEncryption(encodedMsg6, sub3, sub4);
	Input.writeFile("Encode7.txt", encodedMsg7);

	// decoding message
    String file2 = Input.readFile("Encode3.txt");

  }
  
  // Cipher by a rotation of 90 degrees
  String rotate90(String text) {
    char[][] tbl = new char[4][4];
    int index = 0;
    for (int x = 0; x < 4; x++) {
      for (int i = 0; i < 4; i++) {
        if (index < text.length()) {
          tbl[x][i] = text.charAt(index++);
        } else {
          tbl[x][i] = 'b'; 
        }
      }
    }
	
    String bld = "";
    for (int col = 0; col < 4; col++) {
      for (int row = 3; row >= 0; row--) {
        bld += tbl[row][col];
      }
      bld += "b/"; 
    }
    return bld;
  }
  
  // Cipher a shift by reversing the alphabet by a variable that counts down and resets by 3 before keying
  String reverseShiftCipher(String text) {
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String reversed = "";
    for (int x = alphabet.length() - 1; x >= 0; x--) {
      reversed += alphabet.charAt(x);
    }
	int shift = 3;
    String result = "";

    for (int x = 0; x < text.length(); x++) {
      char ch = text.charAt(x);
      if (Character.isLetter(ch)) {
        int index = reversed.indexOf(Character.toUpperCase(ch));
        int shiftedIndex = (index + shift) % 26;
        result += reversed.charAt(shiftedIndex);
        shift--;
        if (shift < 0) shift = 3;
      } else{
        result += ch;
      }
    }
    return result;
  }
  
  // Cipher implementation that skips every 5th character
  String skipByFiveCipher(String text) {
    String result = "";
    for (int x = 0; x < text.length(); x++) {
      int newIndex = (x * 5) % text.length();
      result += text.charAt(newIndex);
    }
    return result;
  }
  
  String decode(String txt){
    String bld="";
    int ascii;
    char ch='\0';
    for(int x=0; x<=txt.length()-1;x++){
      ch=txt.charAt(x);
      ascii=(int)ch;
      ascii-=1;
      bld+= (char)ascii;
    }
    return bld;
  }
  
  int indexOf(char ch, char[] arry){
    for(int x=0; x<=arry.length-1; x++){
      if(arry[x]==ch){
        return x;
      }
    }
    return -1;
  }
  int randInt(int lower, int upper){
    int range = upper - lower;
    return (int)(Math.random()*range+lower);
  }
  
  //Substituiton cipher for E and A with UniCode Mapping
  String subEncryption(String s, char[] sub3, char[] sub4) {
    String bld = ""; 
    char ch = '\0';
    int index = 0;
    for (int x = 0; x <= s.length() - 1; x++) {
      ch = s.charAt(x);
      index = indexOf(ch, sub3);
      if (index != -1) {
        bld += sub4[index];
      } else {
        bld += ch;
      }
    }
    return bld;
  }
  
  //Cut the character by third - Yunzhao
    String cutByThirds(String txt) {
        int length = txt.length();
        int third = length / 3;
        String p1 = txt.substring(0, third);
        String p2 = txt.substring(third, Math.min(2 * third, length));
        String p3 = txt.substring(2 * third);
        return p1 + "/" + p2 + "/" + p3;
    }
	
	//Swap every two characters
    String swapEveryTwo(String txt) {
        String swapped = "";
		char ch1, ch2;
		
		for(int x = 0; x < txt.length() - 1; x += 2){
			ch1 = txt.charAt(x);
			ch2 = txt.charAt(x + 1);
			swapped += ch2;
			swapped += ch1;
		}
		if(txt.length() % 2 != 0){
			swapped += txt.charAt(txt.length() - 1);
		}
		return swapped;
    }
	
	//Padding 
	String addFillerCharacter(String txt){
       while (txt.length() % 5 != 0) {
            txt += 'X';
        }
        return txt;
   }
}

