package com.example.ihaveachildren;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
TextView ans, Message,Key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ans=findViewById(R.id.textView);
        Message=findViewById(R.id.editText);
        Key=findViewById(R.id.editText2);

    }

    public void Caerer(View view) {
        ans.setText(new Task(Message.getText().toString(),Key.getText().toString()).Caeser());
    }

    public void Monoalphabetic(View view) {
        ans.setText(new Task(Message.getText().toString(),Key.getText().toString()).Monoalphabetic());
    }

    public void Railfence(View view) {
        ans.setText(new Task(Message.getText().toString(),Key.getText().toString()).Railfence());
    }

    public void Vigenere(View view) {
        ans.setText(new Task(Message.getText().toString(),Key.getText().toString()).Vigenere());

    }

    public void RC5(View view) throws Exception {
        ans.setText(new Task(Message.getText().toString(),Key.getText().toString()).RC5());
    }

    public void play(View view) {
        ans.setText(new Task(Message.getText().toString(),Key.getText().toString()).play_fair());
    }

    public void poly(View view) {
        ans.setText(new Task(Message.getText().toString(),Key.getText().toString()).polyalphbitac()
        );
    }
}
class Task{
    String Message,Key;
    Task(String Message,String Key){
        this.Message=Message;
        this.Key=Key;
    }
    String play_fair(){
        Map<Character,Integer>col=new HashMap<>(),raw=new HashMap<>();
        char table[][]=new char[5][5];
        boolean visit[]=new boolean[26];
        Key.toLowerCase();Message.toLowerCase();
        int c=0,r=0;
        for(int i=0;i<Key.length();i++){
            if(!visit[Key.charAt(i)-'a']){
                raw.put(Key.charAt(i),r);
                col.put(Key.charAt(i),c);
                table[r][c]=Key.charAt(i);
                c++;
                if(c==5){
                    c=0;r++;
                }
                visit[Key.charAt(i)-'a']=true;
            }
        }
        for(int i=0;i<26;i++){
            if(!visit[i]){
                if(r==5)
                    r=c=0;
                table[r][c]=(char)(i+'a');
                raw.put((char)(i+'a'),r);
                col.put((char)(i+'a'),c);
                c++;
                if(c==5){
                    c=0;r++;
                }
                visit[i]=true;
            }
        }
        String sd="";
        for(int i=0;i<Message.length()-1;i+=2){
            if(Message.charAt(i)==Message.charAt(i+1))
                Message=Message.substring(0,i+1)+"x"+Message.substring(i+1);
        }
        if(Message.length()%2==1)
            Message+='x';
        for(int i=0;i<Message.length();i+=2){
            char cc=Message.charAt(i),vv=Message.charAt(i+1);
            if(raw.get(cc)==raw.get(vv)){
                int rr=raw.get(cc);
                rr++;
                rr%=5;
                int n=col.get(cc);
                sd+=table[r][n];
                n=col.get(vv);
                sd+=table[r][n];
            } else if(col.get(cc)==col.get(vv)){
                int rr=col.get(cc);
                rr++;
                rr%=5;
                sd+=table[raw.get(cc)][r];
                sd+=table[raw.get(vv)][r];
            }
            else{
                sd+=table[raw.get(cc)][col.get(vv)];
                sd+=table[raw.get(vv)][col.get(cc)];
            }
        }
        return  sd;
    }
    String polyalphbitac(){
        for(char i:Key.toCharArray())
            if(i<'0'||i>'9')
                return "Wrong Key .";
        StringBuilder New_Message=new StringBuilder("");
        for(int i=0;i<Message.length();i++)
            New_Message.append((char)('a'+(Message.charAt(i)+Key.charAt(i%Key.length())-'a'-'0')%26));
        return New_Message.toString();
    }

    String Caeser(){
        for(char i:Key.toCharArray())
        if(i<'0'||i>'9')
        return " Wrong Key it should be Number .";
        int Key =Integer.parseInt(this.Key);
        StringBuilder New_Message=new StringBuilder();
        for(int i=0;i<Message.length();i++)
          New_Message.append((char)('a'+(Message.charAt(i)-'a'+Key)%26));
        return New_Message.toString();
    }
    String Monoalphabetic(){
        if(Key.length()!=26)
            return "Wrong Key";
        StringBuilder New_Message=new StringBuilder("");
        for(int i=0 ;i<Message.length();i++){
            New_Message.append(Key.charAt(Message.charAt(i)-'a'));
        }
        return New_Message.toString();
    }
    String Railfence(){
        for(char i:Key.toCharArray())
            if(i<'0'||i>'9')
                return "Wrong Key it should be Number .";
        int Key =Integer.parseInt(this.Key);
        StringBuilder New_Message=new StringBuilder("");
        StringBuilder[]Build=new StringBuilder[Key];
        for (int i=0;i<Key;i++)
            Build[i]=new StringBuilder();
        for(int i=0;i<Message.length();i++)
            Build[i%Key].append(Message.charAt(i));
        for (int i=0;i<Key;i++)
            New_Message.append(Build[i]);
        return New_Message.toString();
    }
    String Vigenere(){
        for(char i:Key.toCharArray())
            if(i<'a'||i>'z')
                return "Wrong Key .";
        StringBuilder New_Message=new StringBuilder("");
        for(int i=0;i<Message.length();i++)
            New_Message.append((char)('a'+(Message.charAt(i)+Key.charAt(i%Key.length())-'a'-'a')%26));
        return New_Message.toString();
    }

    String RC5() throws Exception{
        KeyExp ke = new KeyExp();
        String s[] = ke.compute();
        Message = fullfill0(Long.toBinaryString(Long.parseLong(Message, 16)));
        System.out.print("b = ");
        Key = fullfill0(Long.toBinaryString(Long.parseLong(this.Key, 16)));
        int tmp = 0;
        for (int i = 12; i >= 1; i--) {
            Key = fullfill0(Long.toBinaryString((Long.parseLong(Key, 2) - Long.parseLong(s[(2*i)+1], 2))));
            Key = Key.substring(Key.length()-32);
            tmp = Integer.parseInt(""+Long.parseLong(Message,2)%32);
            Key = Key.substring(Key.length()-tmp) + Key.substring(0,Key.length()-tmp);
            Key = xor(Key, Message);
            Message = fullfill0(Long.toBinaryString((Long.parseLong(Message, 2) - Long.parseLong(s[2*i], 2) )));
            Message = Message.substring(Message.length()-32);
            tmp = Integer.parseInt(""+Long.parseLong(Key,2)%32);
            Message = Message.substring(Message.length()-tmp) + Message.substring(0,Message.length()-tmp);
            Message = xor(Message, Key);
            System.out.println(i+ " iteration = "+(Long.toHexString(Long.parseLong(Message,2)))+(Long.toHexString(Long.parseLong(Key,2))));
        }
        Message = fullfill0(Long.toBinaryString((Long.parseLong(Message, 2) - Long.parseLong(s[0], 2))));
        Key = fullfill0(Long.toBinaryString((Long.parseLong(Key, 2) - Long.parseLong(s[1], 2))));
        String output = Message+Key;
        output = output.substring(output.length()-64);
        return((Long.toHexString(Long.parseLong(output.substring(0,32),2)))+(Long.toHexString(Long.parseLong(output.substring(32),2))));
    }

    public String fullfill0(String x) {
        return (get0(32-x.length())+ x);
    }

    public String xor(String x, String y) {
        String result = "";
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) == y.charAt(i)) {
                result += "0";
            } else {
                result += "1";
            }

        }
        return result;
    }

    public String get0(int len) {
        String result = "";
        for (int i = 0; i < len; i++) {
            result += "0";
        }
        return result;
    }

}
class KeyExp {
    String s[] = new String[26];
    public static void main(String[] args) throws Exception{
        KeyExp k = new KeyExp();
        k.compute();
        for (int i = 0; i < 26; i++) {
            System.out.println(k.s[i]);
        }

    }
    public String[] compute() throws Exception {

        String ukey = fullfill0("000");
        //String s[] = new String[26];
        String l[] = new String[4];
        s[0] = Long.toBinaryString(Long.parseLong("B7E15163", 16));//(Pw)
        for (int i = 1; i < s.length; i++) {
            s[i] = add(s[i - 1], fullfill0(Long.toBinaryString(Long.parseLong("9E3779B9", 16))));//S[i] = S[i-1]+ 0x9E3779B9 (Qw)
        }
        for (int i = 0; i< l.length ; i++) {
            l[i] = (Long.toBinaryString(Long.parseLong(ukey.substring((3-i) * 8, (((3-i) + 1) * 8)), 16)));//S[i] = S[i-1]+ 0x9E3779B9 (Qw)
        }
        for (int i = 0; i< l.length ; i++) {
            System.out.println("l = "+(Long.toHexString(Long.parseLong(l[i],2))));
        }

        int i = 0, j = 0;
        String a = "", b = "",temp = "";
        for (int count = 0; count < 78; count++) {
            //A = S[i] = (S[i] + A + B) <<< 3;
            temp = add(fullfill0(s[i]) ,add(fullfill0(a), fullfill0(b)));
            a = s[i] = temp.substring(3)+temp.substring(0,3);
            //B = L[j] = (L[j] + A + B) <<< (A + B);
            temp = add(fullfill0(l[j]) ,add(fullfill0(a), fullfill0(b)));
            long le  = (Long.parseLong(add(fullfill0(a), fullfill0(b)),2))%32;
            int len  = Integer.parseInt(""+le);
            b = l[j] = temp.substring(len)+temp.substring(0,len);
            i = (i + 1) % 26;
            j = (j + 1) % 4;
        }
        return s;
    }
    public String fullfill0(String x) {
        return (get0(32 - x.length()) + x);
    }

    public String get0(int len) {
        String result = "";
        for (int i = 0; i < len; i++) {
            result += "0";
        }
        return result;
    }

    public String add(String x, String y) {
        String result = "";
        boolean carry = false;
        for (int i = x.length()-1; i >= 0; i--) {
            if ((x.charAt(i) == y.charAt(i) && carry == false) || (x.charAt(i) != y.charAt(i) && carry == true)) {
                result = "0" + result;
            } else {
                result = "1" + result;
            }
            if ((x.charAt(i) == '1' && y.charAt(i) == '1')
                    || (x.charAt(i) == '1' && y.charAt(i) == '1' && carry == true)
                    || (x.charAt(i) != y.charAt(i) && carry == true)) {
                carry = true;
            } else {
                carry = false;
            }
        }
        return result;
    }

}
