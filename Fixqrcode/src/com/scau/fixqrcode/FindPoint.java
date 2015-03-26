package com.scau.fixqrcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

public class FindPoint {
	HashSet<Point> point = new HashSet<Point>();
	List<Point> apoint = new ArrayList<Point>();
	List<Point> bpoint = new ArrayList<Point>();
	List<Point> cpoint = new ArrayList<Point>();
	List<Point> dpoint = new ArrayList<Point>();
	List<Point> epoint = new ArrayList<Point>();
	List<Point> fpoint = new ArrayList<Point>();
	List<Point> gpoint = new ArrayList<Point>();
	int m, n;

	public Point[] findPoint(final Mat iMat) {

		n = iMat.width();
		m = iMat.height();
		// Point[] pointA=new Point[4];
		long startMili = System.currentTimeMillis();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 从上扫描
				int count = 0;
				for (int j = 0; j < m; j++) {
					for (int i = 0; i < n; i++) {
						if (iMat.get(j, i)[0] == 0) {
							apoint.add(new Point(i, j));
							count++;
						}
					}
					if (count >= 1)
						break;
				}
				// 如果只有1个点
				if (count == 1) {

					// 点坐标获取
					point.add(apoint.get(0));
				} else {

					// 点坐标获取
					point.add(apoint.get(0));
					point.add(apoint.get(apoint.size() - 1));
				}

				// 从右上扫描
				int count3 = 0;

				// Point[] pointG=new Point[4];
				int testy3 = n - 1;
				int i7 = 0;
				for (int num = 0; num < m; num++) {
					i7 = num;
					for (int j = n - 1; j >= testy3; j--) {
						if (iMat.get(i7, j)[0] == 0) {
							gpoint.add(new Point(j, i7));
							count3++;
						}
						i7--;
					}
					if (--testy3 < 0)
						break;
					if (count3 >= 1)
						break;
				}
				if (count3 == 1) {

					point.add(gpoint.get(0));
				} else {

					point.add(gpoint.get(0));
					point.add(gpoint.get(gpoint.size() - 1));
				}

			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 从左扫描
				int count = 0;

				// Point[] pointB=new Point[4];
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						if (iMat.get(j, i)[0] == 0) {
							bpoint.add(new Point(i, j));
							count++;
						}
					}
					if (count >= 1)
						break;
				}
				// 如果只有1个点
				if (count == 1) {
					// 点坐标获取
					point.add(bpoint.get(0));
				} else {
					// 点坐标获取
					point.add(bpoint.get(0));
					point.add(bpoint.get(bpoint.size() - 1));
				}

				// 从左上扫描

				int count1 = 0;

				// Point[] pointE=new Point[4];
				int testy = 0;
				int i5 = 0;
				for (int num = 0; num < m; num++) {
					i5 = num;
					for (int j = 0; j < testy; j++) {
						if (iMat.get(i5, j)[0] == 0) {
							epoint.add(new Point(j, i5));
							count1++;
						}
						i5--;
					}
					if (++testy > n)
						break;

					if (count1 >= 1)
						break;
				}
				if (count1 == 1) {

					point.add(epoint.get(0));
				} else {
					point.add(epoint.get(0));
					point.add(epoint.get(epoint.size() - 1));
				}

			}
		}).start();
		// Core.circle(iMat, pointA[0], 30, new Scalar(100,100,100));

		// Core.circle(iMat, pointB[0], 30, new Scalar(100,100,100));
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 从下扫描
				int count = 0;

				// Point[] pointC=new Point[4];
				for (int j = m - 1; j >= 0; j--) {
					for (int i = n - 1; i >= 0; i--) {
						if (iMat.get(j, i)[0] == 0) {
							cpoint.add(new Point(i, j));
							count++;
						}
					}
					if (count >= 1)
						break;
				}
				// 如果只有1个点
				if (count == 1) {
					// 点坐标获取
					point.add(cpoint.get(0));
				} else {
					// 点坐标获取
					point.add(cpoint.get(0));
					point.add(cpoint.get(cpoint.size() - 1));
				}
			}
		}).start();

		// Core.circle(iMat, pointC[0], 30, new Scalar(100,100,100));

		// 从右扫描
		int count = 0;
		// Point[] pointD=new Point[4];
		for (int i = n - 1; i >= 0; i--) {
			for (int j = 0; j < m; j++) {
				if (iMat.get(j, i)[0] == 0) {
					dpoint.add(new Point(i, j));
					count++;
				}
			}
			if (count >= 1)
				break;
		}
		// 如果只有1个点
		if (count == 1) {

			// 点坐标获取
			point.add(dpoint.get(0));
		} else {

			// 点坐标获取
			point.add(dpoint.get(0));
			point.add(dpoint.get(dpoint.size() - 1));
		}

		// Core.circle(iMat, pointD[0], 30, new Scalar(100,100,100));

	
		// 从左下扫描
		int count2 = 0;

		// Point[] pointF=new Point[4];
		int testy2 = 0;
		int i6 = 0;
		for (int num = m - 1; num >= 0; num--) {
			i6 = num;
			for (int j = 0; j < testy2; j++) {
				if (iMat.get(i6, j)[0] == 0) {
					fpoint.add(new Point(j, i6));
					count2++;
				}
				i6++;
			}
			if (++testy2 > n)
				break;
			if (count2 >= 1)
				break;
		}
		if (count2 == 1) {
			point.add(fpoint.get(0));
		} else {
			point.add(fpoint.get(0));
			point.add(fpoint.get(fpoint.size() - 1));
		}

		long endMili = System.currentTimeMillis();
		System.out.println("扫描耗时为：" + (endMili - startMili) + "毫秒");

		// 求三点坐标
		startMili = endMili;
		Point[] abcPoint = new Point[4];
		Point[] pointArray = new Point[12];
		pointArray = point.toArray(new Point[12]);
		double ap, bp, cp;
		ap = Math.sqrt(pointArray[0].x * pointArray[0].x + pointArray[0].y
				* pointArray[0].y);
		bp = Math.sqrt((pointArray[0].x - n) * (pointArray[0].x - n)
				+ pointArray[0].y * pointArray[0].y);
		;
		cp = Math.sqrt(pointArray[0].x * pointArray[0].x
				+ (pointArray[0].y - m) * (pointArray[0].y - m));
		;
		for (int i = 0; i < 3; i++) {
			abcPoint[i] = new Point();
			abcPoint[i].x = pointArray[0].x;
			abcPoint[i].y = pointArray[0].y;
		}
		for (int i = 1; i < point.size(); i++) {
			if (pointArray[i] != null) {
				if (ap > (Math.sqrt(pointArray[i].x * pointArray[i].x
						+ pointArray[i].y * pointArray[i].y))) {
					ap = Math.sqrt(pointArray[i].x * pointArray[i].x
							+ pointArray[i].y * pointArray[i].y);
					abcPoint[0] = pointArray[i];
					pointArray[i] = null;
				}
			}
		}
		for (int i = 1; i < point.size(); i++) {
			if (pointArray[i] != null) {
				if (bp > (Math.sqrt((pointArray[i].x - n)
						* (pointArray[i].x - n) + pointArray[i].y
						* pointArray[i].y))) {
					bp = Math.sqrt((pointArray[i].x - n)
							* (pointArray[i].x - n) + pointArray[i].y
							* pointArray[i].y);
					abcPoint[1] = pointArray[i];
					pointArray[i] = null;
				}
			}
		}
		for (int i = 1; i < point.size(); i++) {
			if (pointArray[i] != null) {
				if (cp > (Math.sqrt(pointArray[i].x * pointArray[i].x
						+ (pointArray[i].y - m) * (pointArray[i].y - m)))) {
					cp = Math.sqrt(pointArray[i].x * pointArray[i].x
							+ (pointArray[i].y - m) * (pointArray[i].y - m));
					abcPoint[2] = pointArray[i];
					pointArray[i] = null;
				}
			}
		}
		endMili = System.currentTimeMillis();
		System.out.println("求三顶点耗时为：" + (endMili - startMili) + "毫秒");
		System.out.println("a:" + abcPoint[0] + " b:" + abcPoint[1] + " c:"
				+ abcPoint[2]);
		startMili = endMili;

		List<Point> bpoint = new ArrayList<Point>();
		bpoint.add(new Point(abcPoint[1].x, abcPoint[1].y));
		int i8 = (int) abcPoint[1].y + 5;
		int j8 = n - 1;
		double k = 0.0000001;
		for (j8 = n - 1; j8 >= 0; j8--) {
			if (iMat.get(i8, j8)[0] == 0) {
				k = (j8 - abcPoint[1].x) / (i8 - abcPoint[1].y);
				bpoint.add(new Point(j8, i8));
				break;
			}
		}

		double k1 = 0.00000001;
		int j9 = n - 1;
		for (int i = (int) abcPoint[1].y + 10; i < abcPoint[1].y + 60; i = i + 5) {
			for (j9 = n - 1; j9 >= 0; j9--) {
				if (iMat.get(i, j9)[0] == 0) {
					k1 = (j9 - j8) / (i - i8);
					if (Math.abs(k1 - k) <= 0.5) {
						bpoint.add(new Point(j9, i));
					}
					break;
				}
			}
			if (Math.abs(k1 - k) <= 0.5) {
				k = k1;
				i8 = i;
				j8 = j9;
			} else
				break;
		}

		List<Point> cpoint = new ArrayList<Point>();
		double k2 = 0.00000001;
		cpoint.add(new Point(abcPoint[2].x, abcPoint[2].y));
		int j10 = (int) abcPoint[2].x + 5;
		int i10 = m - 1;
		for (i10 = m - 1; i10 >= 0; i10--) {
			if (iMat.get(i10, j10)[0] == 0) {
				k2 = (i10 - abcPoint[2].y) / (j10 - abcPoint[2].x);
				cpoint.add(new Point(j10, i10));
				break;
			}
		}

		int j11 = (int) abcPoint[2].x + 10;
		int i11 = m - 1;
		double k3 = 0.0000001;
		for (j11 = (int) abcPoint[2].x + 10; j11 < abcPoint[2].x + 60; j11 = j11 + 5) {
			for (i11 = m - 1; i11 >= 0; i11--) {
				if (iMat.get(i11, j11)[0] == 0) {
					k3 = (i11 - i10) / (j11 - j10);
					if (Math.abs(k3 - k2) <= 0.5) {
						cpoint.add(new Point(j11, i11));
					}
					break;
				}
			}
			if (Math.abs(k3 - k2) <= 0.5) {
				k2 = k3;
				i10 = i11;
				j10 = j11;
			} else
				break;
		}
		endMili = System.currentTimeMillis();
		System.out.println("取样耗时为：" + (endMili - startMili) + "毫秒");
		Linew l = new Linew();
		double[] bx = new double[bpoint.size()];
		double[] by = new double[bpoint.size()];
		double[] cx = new double[cpoint.size()];
		double[] cy = new double[cpoint.size()];
		for (int i = 0; i < bpoint.size(); i++) {
			if (bpoint.get(i) != null) {
				bx[i] = bpoint.get(i).x;
				by[i] = bpoint.get(i).y;
				// System.out.println("bx["+i+"]:"+bx[i]+" by["+i+"]:"+by[i]);
			}
		}
		for (int i = 0; i < cpoint.size(); i++) {
			if (cpoint.get(i) != null) {
				cx[i] = cpoint.get(i).x;
				cy[i] = cpoint.get(i).y;
				// System.out.println("cx["+i+"]:"+cx[i]+" cy["+i+"]:"+cy[i]);
			}
		}
		double[] ab = new double[2];
		double[] cd = new double[2];
		startMili = System.currentTimeMillis();// 当前时间对应的毫秒数
		l.estimate(bx, by, ab);
		l.estimate(cx, cy, cd);
		double x, y;
		x = (cd[1] - ab[1]) / (ab[0] - cd[0]);
		y = x * ab[0] + ab[1];
		endMili = System.currentTimeMillis();
		System.out.println("线性回归耗时为：" + (endMili - startMili) + "毫秒");
		abcPoint[3] = new Point(Math.round(x), Math.round(y));
		Core.circle(iMat, abcPoint[3], 5, new Scalar(100, 100, 100), -1);
		System.out.println("dp:" + abcPoint[3]);
		System.out.println("abcd=" + ab[0] + " " + ab[1] + " " + cd[0] + " "
				+ cd[1]);
		return abcPoint;
	}
}
