package com.scau.fixqrcode;

/**
 * 最小二乘法 线性回归
 * y = a x + b 
 * 
 * b = sum( y ) / n - a * sum( x ) / n
 * 
 * a = ( n * sum( xy ) - sum( x ) * sum( y ) ) / ( n * sum( x^2 ) - sum(x) ^ 2 )
 * 
 *
 */
public class Linew{

	
	/**
	 * 预测
	 * @param x
	 * @param y
	 * @param c
	 */

	public  void estimate( double[] x , double[] y ,double[] c) {
		double a = getXc( x , y ) ;
		double b = getC( x , y , a ) ;
		c[0]=a;
		c[1]=b;
	}
	
	/**
	 * 计算 x 的系数
	 * @param x
	 * @param y
	 * @return
	 */
	public  double getXc( double[] x , double[] y ){
		int n = x.length ;
		System.out.println("n:"+n);
		return ( n * pSum( x , y ) - sum( x ) * sum( y ) ) 
				/ ( n * sqSum( x ) - Math.pow(sum(x), 2) ) ;
	}
	
	/**
	 * 计算常量系数
	 * @param x
	 * @param y
	 * @param a
	 * @return
	 */
	public  double getC( double[] x , double[] y , double a ){
		int n = x.length ;
		return sum( y ) / n - a * sum( x ) / n ;
	}
	
	/**
	 * 计算常量系数
	 * @param x
	 * @param y
	 * @return
	 */
	public  double getC( double[] x , double[] y ){
		int n = x.length ;
		double a = getXc( x , y ) ;
		return sum( y ) / n - a * sum( x ) / n ;
	}
	
	private  double sum(double[] ds) {
		double s = 0 ;
		for( double d : ds ) s = s + d ;
		return s ;
	}
	
	private double sqSum(double[] ds) {
		double s = 0 ;
		for( double d : ds ) s = s + Math.pow(d, 2) ;
		return s ;
	}
	
	private double pSum( double[] x , double[] y ) {
		double s = 0 ;
		for( int i = 0 ; i < x.length ; i++ ) s = s + x[i] * y[i] ;
		return s ;
	}
}
