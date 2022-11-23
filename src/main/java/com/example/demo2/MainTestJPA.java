package com.example.demo2;

import persistence.entity.Angle;
import persistence.repository.AngleJpaRepository;

public class MainTestJPA {
	
	public static void main(String[] args) {
		
    	Angle angle = AngleJpaRepository.create();
    	System.out.println("main() > 1 " + angle);
    	AngleJpaRepository.deleteByEntity(angle);
    	angle = AngleJpaRepository.create();
    	System.out.println("main() > 2 " + angle);
    	AngleJpaRepository.deleteByIdViaPErsistenceContext(angle.getId());
    	angle = AngleJpaRepository.create();
    	System.out.println("main() > 3 " + angle);
    	AngleJpaRepository.deleteByIdWithJPQL(angle.getId());
    	PersistenceManager.INSTANCE.close();
	}
}
