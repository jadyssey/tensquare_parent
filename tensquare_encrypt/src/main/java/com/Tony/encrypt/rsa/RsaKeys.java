package com.Tony.encrypt.rsa;

/**
 * rsa加解密用的公钥和私钥
 * @author Administrator
 *
 */
public class RsaKeys {

	//生成秘钥对的方法可以参考这篇帖子
	//https://www.cnblogs.com/yucy/p/8962823.html

//	//服务器公钥
//	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6Dw9nwjBmDD/Ca1QnRGy"
//											 + "GjtLbF4CX2EGGS7iqwPToV2UUtTDDemq69P8E+WJ4n5W7Iln3pgK+32y19B4oT5q"
//											 + "iUwXbbEaAXPPZFmT5svPH6XxiQgsiaeZtwQjY61qDga6UH2mYGp0GbrP3i9TjPNt"
//											 + "IeSwUSaH2YZfwNgFWqj+y/0jjl8DUsN2tIFVSNpNTZNQ/VX4Dln0Z5DBPK1mSskd"
//											 + "N6uPUj9Ga/IKnwUIv+wL1VWjLNlUjcEHsUE+YE2FN03VnWDJ/VHs7UdHha4d/nUH"
//											 + "rZrJsKkauqnwJsYbijQU+a0HubwXB7BYMlKovikwNpdMS3+lBzjS5KIu6mRv1GoE"
//											 + "vQIDAQAB";
//
//	//服务器私钥(经过pkcs8格式处理)
//	private static final String serverPrvKeyPkcs8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDoPD2fCMGYMP8J"
//				 								 + "rVCdEbIaO0tsXgJfYQYZLuKrA9OhXZRS1MMN6arr0/wT5YniflbsiWfemAr7fbLX"
//				 								 + "0HihPmqJTBdtsRoBc89kWZPmy88fpfGJCCyJp5m3BCNjrWoOBrpQfaZganQZus/e"
//				 								 + "L1OM820h5LBRJofZhl/A2AVaqP7L/SOOXwNSw3a0gVVI2k1Nk1D9VfgOWfRnkME8"
//				 								 + "rWZKyR03q49SP0Zr8gqfBQi/7AvVVaMs2VSNwQexQT5gTYU3TdWdYMn9UeztR0eF"
//				 								 + "rh3+dQetmsmwqRq6qfAmxhuKNBT5rQe5vBcHsFgyUqi+KTA2l0xLf6UHONLkoi7q"
//				 								 + "ZG/UagS9AgMBAAECggEBANP72QvIBF8Vqld8+q7FLlu/cDN1BJlniReHsqQEFDOh"
//				 								 + "pfiN+ZZDix9FGz5WMiyqwlGbg1KuWqgBrzRMOTCGNt0oteIM3P4iZlblZZoww9nR"
//				 								 + "sc4xxeXJNQjYIC2mZ75x6bP7Xdl4ko3B9miLrqpksWNUypTopOysOc9f4FNHG326"
//				 								 + "0EMazVaXRCAIapTlcUpcwuRB1HT4N6iKL5Mzk3bzafLxfxbGCgTYiRQNeRyhXOnD"
//				 								 + "eJox64b5QkFjKn2G66B5RFZIQ+V+rOGsQElAMbW95jl0VoxUs6p5aNEe6jTgRzAT"
//				 								 + "kqM2v8As0GWi6yogQlsnR0WBn1ztggXTghQs2iDZ0YkCgYEA/LzC5Q8T15K2bM/N"
//				 								 + "K3ghIDBclB++Lw/xK1eONTXN+pBBqVQETtF3wxy6PiLV6PpJT/JIP27Q9VbtM9UF"
//				 								 + "3lepW6Z03VLqEVZo0fdVVyp8oHqv3I8Vo4JFPBDVxFiezygca/drtGMoce0wLWqu"
//				 								 + "bXlUmQlj+PTbXJMz4VTXuPl1cesCgYEA6zu5k1DsfPolcr3y7K9XpzkwBrT/L7LE"
//				 								 + "EiUGYIvgAkiIta2NDO/BIPdsq6OfkMdycAwkWFiGrJ7/VgU+hffIZwjZesr4HQuC"
//				 								 + "0APsqtUrk2yx+f33ZbrS39gcm/STDkVepeo1dsk2DMp7iCaxttYtMuqz3BNEwfRS"
//				 								 + "kIyKujP5kfcCgYEA1N2vUPm3/pNFLrR+26PcUp4o+2EY785/k7+0uMBOckFZ7GIl"
//				 								 + "FrV6J01k17zDaeyUHs+zZinRuTGzqzo6LSCsNdMnDtos5tleg6nLqRTRzuBGin/A"
//				 								 + "++xWn9aWFT+G0ne4KH9FqbLyd7IMJ9R4gR/1zseH+kFRGNGqmpi48MS61G0CgYBc"
//				 								 + "PBniwotH4cmHOSWkWohTAGBtcNDSghTRTIU4m//kxU4ddoRk+ylN5NZOYqTxXtLn"
//				 								 + "Tkt9/JAp5VoW/41peCOzCsxDkoxAzz+mkrNctKMWdjs+268Cy4Nd09475GU45khb"
//				 								 + "Y/88qV6xGz/evdVW7JniahbGByQhrMwm84R9yF1mNwKBgCIJZOFp9xV2997IY83S"
//				 								 + "habB/YSFbfZyojV+VFBRl4uc6OCpXdtSYzmsaRcMjN6Ikn7Mb9zgRHR8mPmtbVfj"
//				 								 + "B8W6V1H2KOPfn/LAM7Z0qw0MW4jimBhfhn4HY30AQ6GeImb2OqOuh3RBbeuuD+7m"
//				 								 + "LpFZC9zGggf9RK3PfqKeq30q";

	//服务器公钥
	private static final String serverPubKey =
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzbWiwoi85MFaa5Uxdxgu" +
			"8A8ogEkL++TvyIGXlPPEYRP5yNY5VFkHxpFdtknkMqswW1lAoED9qucxCrzElOji" +
			"iKBazoOl3FZB4v0axIrLiOkrons3bl+tjSYlhNROOxKsJ9uq2nCBP6mrTh/NOgU0" +
			"qr0PFOeS80OODj2UpK7oIl13izQHd1xwT9B6yhY5lSZHkkdpK2gxdWQoAzdD/lrk" +
			"WVQWy0z39NgyqzdpFnNV0hA+wZGtjvXhHk2nfArxfGvtTz+k1ZLonwnI56Vr3tSn" +
			"Dre3jZJFDC0Lcc5ihUnIU2nPxGaDeG5o+zZlXDHa7CV6SAL8ok1XT4oRPAQIq9Gr" +
			"1QIDAQAB";

	//服务器私钥(经过pkcs8格式处理)
	private static final String serverPrvKeyPkcs8 =
			"MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDNtaLCiLzkwVpr" +
			"lTF3GC7wDyiASQv75O/IgZeU88RhE/nI1jlUWQfGkV22SeQyqzBbWUCgQP2q5zEK" +
			"vMSU6OKIoFrOg6XcVkHi/RrEisuI6SuiezduX62NJiWE1E47Eqwn26racIE/qatO" +
			"H806BTSqvQ8U55LzQ44OPZSkrugiXXeLNAd3XHBP0HrKFjmVJkeSR2kraDF1ZCgD" +
			"N0P+WuRZVBbLTPf02DKrN2kWc1XSED7Bka2O9eEeTad8CvF8a+1PP6TVkuifCcjn" +
			"pWve1KcOt7eNkkUMLQtxzmKFSchTac/EZoN4bmj7NmVcMdrsJXpIAvyiTVdPihE8" +
			"BAir0avVAgMBAAECggEAYG5fSBZVhl7lhj1AHPYnOr3AXLLhfUnK5hFp/5duXZvB" +
			"olZaNaIutPT8GzH0WZRKbOggFX+h6nXKVaA7/xpU53tUVi78l5eP91pzK+NsmlfD" +
			"7r0YNROBBgP13pivu517OowNQa0vyao99beOuz4pfEh4Q3JzPrpSY3aWscSUaVbU" +
			"M6qp+KSYSCzGEt8yfiBkYVBGp6pM3y/UpkNmiHMC5F+0RgmczkoKNcZf8v+yqa3Q" +
			"Ea2XhfORwddzNZEy1oRx597VWh6hTtX1ax4YVbgc+QqC75atVE+m6Wk3vp+P9u8f" +
			"VDlUPNEza+9zdncS0Wjj2Xxw90xKM7tSi33wOyqxGQKBgQDrhkPxGeGZ0+gLnG9F" +
			"9ZjQx6pR6MOm7Iqt2V3AqY7jrhjqZtnVcxYsz2JSg45RQjuoRdt1J8jMJO2RbQly" +
			"yewC1T3Mv/zBswJBjWtVZYTSeN15sF+Z9zDBvNBJAlDThtsFkPxlxOiUXX1qwRo0" +
			"hJoEIJ30+414Exd1P3KFn7KKxwKBgQDfl9IxPGCU8tMiM/UzrLpKS/urFaNA4IEv" +
			"Lypiav/OArhxdOqR6scAcaatROtWPrtTLNpkK7ACWM8exChFuFJqVRHzExp/iY8A" +
			"SR2LUVxXKtBTaWzj4v9LUJCQVqobQlMKoqsf5l/H8Nfdgif8/quy7hzUsLA6KdHN" +
			"AowdU/4YgwKBgC2N9q43l08tkYBNbahWbIxVUjgtkUpEDj66AEw3f4/CKMeNxSxn" +
			"6i4YnqffEjPdX7SYbm4ml8wEiH7MXDt7ms6heCaWK8k7WoBbXhtSW3JqNkxHNPaO" +
			"vTt7zb2RWRjQyKQ2+tqkT2gC/TMcznjB/Rg3RtYPoblL2Amxj/EQhnQZAoGATce0" +
			"mOgV+PqXs9g7PiSiEWJpQ9SdG959UfqoVeA+6F1N6Dnxt5ziaScgAjptW20rIP4D" +
			"YsFH0ykts9gs4kQCd3bsbZCBsUga9NB6dJJy+uer3LhZ5tR6VqrJ/0wGlMD3vkVk" +
			"Hg631wfTL/0tB/dXp0DpyptEUpdloll2gGVNiR0CgYB72Rv6kjcdMsBI1fwbWfZM" +
			"6ARBNc1pXPFz19z+rd865DwIJKH2RWqw0Fg/WovkG98QsfOxiYosN+2Zabkyw34t" +
			"7j1sMLuibFGKxOZ29ewEf/blIbEUDoLmX2J8eXr/mO31gY5N7qeZ6BpvmUR3pnb2" +
			"UonpbUwZuE44A9dYR3CIFw==";

	public static String getServerPubKey() {
		return serverPubKey;
	}

	public static String getServerPrvKeyPkcs8() {
		return serverPrvKeyPkcs8;
	}
	
}
