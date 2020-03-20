package com.andersenlab.aadamovich.meetupprojection.projection.open;

import org.springframework.beans.factory.annotation.Value;

public interface OpenProjectionOwner {

    // Вложенная проекция работает для методов 2 и 3!
    OpenProjectionCar getCar();

    // 1) Через default метод интерфейса. Вложенная проекция не будет работать (по аналогии с закрытой проекцией)!
    String getFirstName();
    String getLastName();

    default String getFullNameDefaultMethod() {
        return getFirstName() + ' ' + getLastName();
    }

    // 2) Через аннотацию @Value обращаемся к полям класса сущности (target)
    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullNameByTargetSpEl();

    //3) Через вызов метода специального бина
    @Value("#{@fullNameSampleBean.getFullName(target)}")
    String getFullNameByObjectBeanSpEl();
}
