package com.greenart.firstproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenart.firstproject.entity.CartInfoEntity;
import com.greenart.firstproject.entity.UserEntity;
import com.greenart.firstproject.repository.CartInfoRepository;
import com.greenart.firstproject.repository.OptionInfoRepository;
import com.greenart.firstproject.vo.cart.CartPlusMinusVO;
import com.greenart.firstproject.vo.cart.CartInfoVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartInfoRepository cartRepo;
    private final OptionInfoRepository optionRepo;

    @Transactional(readOnly = true)
    public List<CartInfoVO> getCartInfo(UserEntity loginUser) {
        return cartRepo.findByUser(loginUser).stream().map(CartInfoVO::fromEntity).toList();
    }

    public void cartAdd(UserEntity loginUser, CartPlusMinusVO data) {
        Optional<CartInfoEntity> existInCart = cartRepo.findByUserAndOptionSeq(loginUser, data.getOptionSeq());
        if(existInCart.isEmpty()) {
            CartInfoEntity newCartInfo = CartInfoEntity.builder()
                .stock(data.getStock())
                .user(loginUser)
                .option(optionRepo.findById(data.getOptionSeq()).orElseThrow())
                .build();
            cartRepo.save(newCartInfo);
            return;
        }
        existInCart.get().addStock(data.getStock());
        cartRepo.save(existInCart.get());
    }

    
    /**
     * 유저 엔티티와 옵션 seq번호를 이용해서 장바구니 속에 담은 요소를 삭제하는 서비스
     * @param loginUser 로그인한 세션 유저
     * @param optionSeq 장바구니 속 삭제할 옵션의 seq번호
     * @return 삭제되었다면 true 안되었다면 false
     */
    public Boolean cartDelete(UserEntity loginUser, Long optionSeq) {
        Optional<CartInfoEntity> element = cartRepo.findByUserAndOptionSeq(loginUser, optionSeq);
        if(element.isEmpty()) {
            return false;
        }
        cartRepo.delete(element.get());
        return true; 
    }

    public void cartSetStock(UserEntity loginUser, CartPlusMinusVO data) {
        cartRepo.findByUserAndOptionSeq(loginUser, data.getOptionSeq()).ifPresent(c -> {
                c.setStock(data.getStock());
                cartRepo.save(c);
            } 
        );
    }
    
}
