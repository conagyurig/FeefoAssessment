package com.feefo;

public class App 
{
    public static void main( String[] args )
    {
        //Examples from the sample code
        String jt = "Java engineer";
        Normaliser n = new Normaliser();
        String normalisedTitle = n.normalise(jt);
        System.out.println(normalisedTitle);
        jt = "C# engineer";
        normalisedTitle = n.normalise(jt);
        System.out.println(normalisedTitle);
        jt = "Chief Accountant";
        normalisedTitle = n.normalise(jt);
        System.out.println(normalisedTitle);
    }
}
