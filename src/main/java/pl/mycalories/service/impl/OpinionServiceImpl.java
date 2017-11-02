package pl.mycalories.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mycalories.dao.OpinionDao;
import pl.mycalories.model.Opinion;
import pl.mycalories.model.Product;
import pl.mycalories.service.OpinionService;
import pl.mycalories.service.ProductService;

import java.util.List;
import java.util.Set;

@Service
public class OpinionServiceImpl extends AbstractServiceImpl<Opinion, Long, OpinionDao>
        implements OpinionService {

    private ProductService productService;

    @Autowired
    public OpinionServiceImpl(OpinionDao repository) {
        super(repository);
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Set<Opinion> getAllOpinions(Long productId) {
        Product product = productService.findById(productId);

        Set<Opinion> opinions = product.getOpinions();
        return opinions;
    }
}
