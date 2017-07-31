package rxandroid.com.model.logic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import rxandroid.com.base.BaseModel;
import rxandroid.com.base.BasePresenter;
import rxandroid.com.base.BaseView;


/**
 * Created by WEQ on 2017/6/12.
 */

public class ContractProxy {
    private static final ContractProxy m_instance = new ContractProxy() ;
    private Map<Class,Object> m_objects ;
    public static ContractProxy getInstance(){
        return m_instance ;
    }

    private ContractProxy (){
        m_objects = new HashMap<>() ;
    }
    public static Class<BasePresenter> getPresenterClazz(final Class clazz, final int index){
        //返回表示此Class所表示的实体（类、接口、基本类型或void）的直接超类Type
        Type genType = clazz.getGenericSuperclass() ;
        if (!(genType instanceof ParameterizedType)){
            return BasePresenter.class ;
        }
        //返回表示此类型实际类型参数的Type对象的数组
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments() ;
        if (index >=params.length|| index < 0){
            return BasePresenter.class ;
        }
        if (!(params[index] instanceof Class)){
            return BasePresenter.class ;
        }
        return (Class)params[index] ;

    }
    /**
     * @explain
     * @author Aaron.Wang.
     * @time 2017/6/13 19:13.
     * @desc 通过反射，获得定义Class时声明的父类的泛型参数的六类线
     * @param
     *
     * */

    public static Class<BaseModel> getModelClazz(final Class clazz, final int index) {

        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return BaseModel.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return BaseModel.class;
        }
        if (!(params[index] instanceof Class)) {
            return BaseModel.class;
        }
        return (Class) params[index];
    }

    /**
     * @explain
     * @author Aaron.Wang.
     * @time 2017/6/13 19:16.
     * @desc 获取presenter
     * @param <T>
     *
     * */
    public <T> T presenter(Class clazz){
        if (!m_objects.containsKey(clazz)){
            initInstance(clazz);
        }
        BasePresenter presenter = null ;
        try{
            presenter = ((BasePresenter)clazz.newInstance()) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return (T)presenter ;
    }

    /**
     * @explain
     * @author Aaron.Wang.
     * @time 2017/6/13 19:17.
     * @desc 进行初始化
     * @param clazz
     *
     * */
    public void initInstance(Class clazz){
        try{
            m_objects.put(clazz,clazz.newInstance()) ;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @explain
     * @author Aaron.Wang.
     * @time 2017/6/13 19:21.
     * @desc 绑定View
     * @param presenter
     * @param <V>
     *
     * */
    public <V> V bindView(BaseView view,BasePresenter presenter){
        if (view !=presenter.getView()){
            if (presenter.getView()!=null){
                presenter.detachView();
            }
            presenter.attachView(view);
        }
        return (V)view ;
    }
    /**
     * @explain
     * @author Aaron.Wang.
     * @time 2017/6/13 19:24.
     * @desc 绑定Presenter
     * @param clazz
     * @param var1
     * @param <T>
     * @return
     *
     * */
    public <T> T bindPresenter(Class clazz,BaseView var1){
        if (!m_objects.containsKey(clazz)){

        }
        BasePresenter presenter = null ;
        try {
            presenter = ((BasePresenter)clazz.newInstance()) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        if (var1!=presenter.getView()){
            if (presenter.getView()!=null){
                presenter.detachView();
            }
            presenter.attachView(var1);
        }
        return (T)presenter ;
    }
    /**
     * @explain
     * @author Aaron.Wang.
     * @time 2017/6/13 19:56.
     * @desc 初始化Model
     * @param
     * @return
     *
     * */
    public <M> M bindModel(Class clazz,BasePresenter presenter){
        if (!m_objects.containsKey(clazz)){
            initInstance(clazz);
        }
        BaseModel model = null ;
        try {
            model = ((BaseModel)clazz.newInstance()) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        if (model!=presenter.getModel()){
            if (presenter.getModel()!=null){
                presenter.detachModel();
            }
            presenter.attachModel(model);
        }
        return (M)presenter ;

    }
    /**
     * @explain
     * @author Aaron.Wang.
     * @time 2017/6/13 20:00.
     * @desc 移除绑定 移除map
     * @param
     * @return
     *
     * */
    public void unbindPresenter(Class clazz,BaseView var1){
        if (m_objects.containsKey(clazz)){
            BasePresenter presenter = null ;
            try {
                presenter = ((BasePresenter)clazz.newInstance()) ;
            }catch (Exception e){
                e.printStackTrace();
            }
            if (var1!=presenter.getView()){
                if (presenter.getView()!=null){
                    presenter.detachView();
                }
                m_objects.remove(clazz) ;
            }
        }
    }

    /**
     * @explain
     * @author Aaron.Wang.
     * @time 2017/6/13 20:04.
     * @desc 解除绑定
     * @param
     * @return
     *
     * */
    public void unbindView(BaseView view,BasePresenter presenter){
        if (view!=presenter.getView()){
            if (presenter.getView()!=null){
                presenter.detachView();
            }
        }
    }

    // 解除绑定 移除map
    public void unbindModel(Class clzz, BasePresenter presenter) {
        if (m_objects.containsKey(clzz)) {
            BaseModel model = null;
            try {
                model = ((BaseModel) clzz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (model != presenter.getModel()) {
                if (presenter.getModel() != null)
                    presenter.detachModel();
                m_objects.remove(clzz);
            }
        }
    }
    /**
     *   Presenter
     * 通过反射, 获得定义Class时声明的父类的泛型参数的类型.
     *
     *@param clazz
     *            clazz The class to introspect
     * @param index
     *            the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be
     *         determined
     */
    @SuppressWarnings("unchecked")
    public static Class<BasePresenter> getPresnterClazz(final Class clazz, final int index) {

        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return BasePresenter.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return BasePresenter.class;
        }
        if (!(params[index] instanceof Class)) {
            return BasePresenter.class;
        }
        return (Class) params[index];
    }


}
