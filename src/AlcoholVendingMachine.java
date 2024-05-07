import java.io.*;
import java.util.*;

abstract class AlcoholVendingMachine											// 상위 클래스
{
	
	
	private AlcoholVendingMachine avmArr[] = new AlcoholVendingMachine[3];     // 상위 클래스 객체배열 생성
	
	
	protected abstract void list();												// 술 리스트
	
	
	protected abstract int alcoholChoose();										// 술을 선택하는 메소드

	
	
	protected abstract void alcoholReturn(int al);								// 술을 반환하는 메소드
	
	
	protected abstract int stock(int al);										// 모든 종류의 술 재고 정리해주는 메소드

	
	protected abstract void enterStock(int al);									// 모든 재고의 양을 초기값으로 초기화해주는 메소드
	
		
	
	
	
	protected abstract int alcoholPrice(int al);								// 술 가격 책정하는 메소드
	
	
	protected static void enterMoney()											// 돈 입력 출력문 메소드
	{
		System.out.print("돈을 입력하시오 : ");   
	}
	
	
	protected int getMoney()													// 돈 입력받는 메소드
	{
		Scanner sc = new Scanner(System.in);  
		int money = sc.nextInt();

		PayMoney pm = PayMoney.getInstance();
		pm.inputMoney(money);

		return money;
	}


	protected abstract int alcoholRun();										// 돈 지불 전까지의 단계를 모두 담은 메소드


	protected abstract int change(int al);										// 고른 술의 최종값을 입력받은 값에서 빼주는 메소드

	


	
	
	
	static void vmRun() throws Exception										// 최종 모든 기능 구현하는 메소드
	{
		Scanner sc = new Scanner(System.in);

		int add;
		int a;
		int al=0;
		int price=0;
		int priceCancle=0;
		int priceAdd1=0;
		int priceAdd2=0;
		int priceAdd3=0;
		int priceAll=0;
		int stock=0;
		int result=0;
		

		
		PrizeMoneyDisplay pmd = new PrizeMoneyDisplay();
		ChangeMoney cm = new ChangeMoney();
		
		
		SingleItem ob1 = new SingleItem();										// 하위 클래스들 객체 생성
        PresetCocktail ob2 = new PresetCocktail();
        CustomCocktail ob3 = new CustomCocktail();
		
		
   	    AlcoholVendingMachine[] avmArr = new AlcoholVendingMachine[3];
		
		avmArr[0] = ob1;														// → SingleItem 객체배열(업캐스팅)
   	    avmArr[1] = ob2;														// → PresetCocktail 객체배열(업캐스팅)
   	    avmArr[2] = ob3;														// → CustomCocktail 객체배열(업캐스팅)

		
	
		while (true)
	    { 
			System.out.print("3가지 유형 중 하나를 선택하세요 (① 단품 / ② 프리셋(칵테일) / ③ 커스텀(칵테일) :  "); 
			a = sc.nextInt();
			
			if (a == 1)
			{
				al = avmArr[a-1].alcoholRun();
			    price = avmArr[a-1].alcoholPrice(al);						// SingleItem 클래스에서 받은 술 가격 → price에 대입
				priceAdd1 += price;											// SingleItem 에서 들어오는 가격을 모두 priceAdd1 에 누적
				
		    }
			else if (a == 2)
			{
				al = avmArr[a-1].alcoholRun();	
                price = avmArr[a-1].alcoholPrice(al);						// PresetCocktail 클래스에서 받은 술 가격 → price에 대입
				priceAdd2 += price;                                         // PresetCocktail 에서 들어오는 가격을 모두 priceAdd2 에 누적
				
			}
			else if (a == 3)					
			{
				price = avmArr[a-1].alcoholRun();							// CustomCocktail 클래스에서 받은 술 가격 → price에 대입
				priceAdd3 += price;											// CustomCocktail 에서 들어오는 가격을 모두 priceAdd3 에 누적
				
			}
			priceAll = priceAdd1 + priceAdd2 + priceAdd3 + priceCancle;				// priceAll → 최종 계산할 금액
			System.out.println("결제할 총액" + priceAll);							// priceCancle → 직전에 담은 상품 가격을 priceAll 에서 빼준 금액
			
			
			
			System.out.println();
			System.out.print("[돈 계산(1) / 추가 구매(2) / 방금 선택한 상품 취소(3)] : ");
            add = sc.nextInt();
			
			
			if (add==3)																// 방금 선택한 제품 취소(3)를 선택하였을 때 조건문
			{
				
				
				priceCancle = priceAll - price;										// (3)을 누르기 직전 선택한 상품만 최종 계산할 금액에서 빼준 금액
				//System.out.println("최근구입 상품 뺀 총액" + priceCancle);
				
				
				priceAdd1 = 0;
				priceAdd2 = 0;														// 직전에 선택한 상품을 제외시킨 후 최종 가격 금액을 priceCancle에 담았기 때문에	
				priceAdd3 = 0;                                                      // 위에서 가격 누적 시켰던 priceAdd1,priceAdd2,priceAdd3 은 0으로 초기화 하고 다시 담아야 한다.

			}
			
			if (add==1)																// 돈 계산(1)을 선택하면 break 후 while문을 빠져나가 돈 입력 화면으로 넘어간다.
			{	
				/*
				stock = avmArr[a-1].stock(al);
			    if (stock < 100)
		        {
			        //enterStock(al);
		        }
				*/
				
				break;	
				
			}
			
			/*
			if (add==2)
			{
				
				stock = avmArr[a-1].stock(al);
				if (stock < 100)
		        {
			        //enterStock(al);
		        }
				
				
			
			}
			*/
      		 
     	} // close while															
      	
		System.out.println();
	
				
		enterMoney();																// 돈 입력을 위한 출력문
		cm.returnMoney(avmArr[a-1].change(priceAll),pmd);							// 최종가격 priceAll을 change메소드에 담아 잔돈 계산
					
	}




}
