package test.pattern.bridgePattern;

public interface ICoffeeAdditives {
    void addSomething();
}



//加奶
 class Milk implements ICoffeeAdditives {
    @Override
    public void addSomething() {
        System.out.println("加奶");
    }
}
 
 
//加糖
 class Sugar implements ICoffeeAdditives {
    @Override
    public void addSomething() {
        System.out.println("加糖");
    }
}