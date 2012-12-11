package eu.adf.fwk.utils;


import eu.adf.fwk.query.Criteria;
import eu.adf.fwk.query.CriteriaExpression;
import eu.adf.fwk.query.Pagination;
import eu.adf.fwk.query.QueryRequest;

import eu.adf.fwk.query.QueryResponse;
import eu.adf.fwk.query.UnaryOperationCode;
import eu.adf.fwk.query.ValueExpression;

import java.math.BigInteger;

import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.event.SortEvent;

public class TablePaginationUtils {// <T extends QueryRequest>{
    
    private int pageNumber=1;
    private Integer rangeSize=10;
//    private List results;
    private String operationBinding;
    private QueryRequest request;  
    
    private RichCommandImageLink firstPageUIComp;
    private RichCommandImageLink previousPageUIComp;
    private RichCommandImageLink nextPageUIComp;
    private RichCommandImageLink lastPageUIComp;
    private RichSelectOneChoice rangeSizeUIComp;
    private RichInputText pageNoUIComp;
    private RichOutputText lastPageNoUIComp;
    private RichTable tableUIComp;

    private int totalRowsCount;
    
    public TablePaginationUtils(String operationBinding) {
        this.operationBinding = operationBinding;
        this.request = new QueryRequest();
        request.setPagination( new Pagination()); 
    }
    
    public void queryListener(QueryEvent queryEvent) {
        
        FilterableQueryDescriptor fqd = (FilterableQueryDescriptor) queryEvent.getDescriptor();
        Map filterMap = fqd.getFilterCriteria();  
        Boolean hasCriteria = false;
        request.getCriterias().clear();

        Criteria criteria = new Criteria();
        CriteriaExpression expression = new CriteriaExpression();
        criteria.setCriteriaExpression(expression);
        
        for (Object filter:filterMap.keySet()){
            if (!StringUtils.isBlank((String) filterMap.get(filter))) {
                ValueExpression valueExpresion = new ValueExpression();            
                valueExpresion.setCriteriaAttribute((String)filter);
                valueExpresion.setOperationCode(UnaryOperationCode.LIKE_IGNORE_CASE);        
                valueExpresion.getValues().add((String) filterMap.get(filter));             
                expression.getValueExpressions().add(valueExpresion);
                hasCriteria = true;
            }
        }
        
        if (hasCriteria) {
            request.getCriterias().add(criteria);
            this.pageNumber = 1;
            
        }
        
        doQuery(request);
    }
    
    public void sortListener(SortEvent sortEvent) {
        QueryRequest request=null;
        doQuery(request);
    }
    
    
    public void rangeSizeListener(ValueChangeEvent valueChangeEvent) {
        Integer newRangeSize = null;
        try{
            newRangeSize= (Integer)valueChangeEvent.getNewValue();
        }catch(Exception e){}
        
        if (newRangeSize!=null) {
            this.setRangeSize(newRangeSize);
            this.pageNumber = 1;
            refreshPagination();
        }
        
    }
    
    public void pageNumberListener(ValueChangeEvent valueChangeEvent) {
        Integer newPageNumber = null;
        try{
            newPageNumber= (Integer)valueChangeEvent.getNewValue();
        }catch(Exception e){}
        
        if (newPageNumber!=null) {
            this.pageNumber = newPageNumber;
            refreshPagination();
        }
    }
    
    
    /**
     * Invoked when user click to go to the last publicationPage
     * @return
     */
    public void goFirstPage(){
        pageNumber=1;
        refreshPagination();  

    }
    
    /**
     * Invoked when user click to go to the next publicationPage
     * @return
     */
    public void goNextSet(){
        pageNumber+=1;
        refreshPagination();        

    }
    
    
    /**
     * Invoked when user click to go to the previous publicationPage
     * @return
     */
    public void goPreviousSet(){
        if (pageNumber>1){
            pageNumber-=1;
            refreshPagination();            
        }
    }
    
    /**
     * Invoked when user click to go to the last publicationPage
     * @return
     */
    public void goLastPage(){
        pageNumber = totalRowsCount/rangeSize;
        if (totalRowsCount%this.getRangeSize()>0){
            pageNumber+=1;
        }
        refreshPagination(); 
    }
    
    /**
     * Get the last publicationPage number
     * @return
     */
    public Integer getLastPage(){
        Integer lastPage = totalRowsCount/rangeSize;
        if (totalRowsCount%this.getRangeSize()>0){
            lastPage+=1;
        }
        return lastPage;
    }
    
    /**
     * Is go to previous publicationPage enabled
     * @return
     */
    public boolean isPreviousEnabled(){
        if (pageNumber>1){
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Is go to next publicationPage enabled
     * @return
     */
    public boolean isNextEnabled(){
        if (pageNumber<getLastPage()){
            return true;
        }
        else {
            return false;
        }
    }
    
    
    
    
    
    
    
    public void refreshPagination(){
        Pagination pag= request.getPagination();
        pag.setFetchSize(BigInteger.valueOf(Integer.valueOf(this.getRangeSize())));
        pag.setStartPosition(BigInteger.valueOf(this.getRangeSize()*(pageNumber-1)));
        
        doQuery(request);
    }
    
    
    
    
    
    
    
    private void doQuery(QueryRequest request) {
        //String[] paginationItems = new String[]{ "pt_it1","pt_ot1","msTable"};

        
        
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        for (String item:paginationItems) {
//            UIComponent component = getUIComponent(facesContext.getViewRoot(),item);
//                     //FacesContext.getCurrentInstance().getViewRoot().findComponent(item);
//              if (component != null)
//              {
//                 adfFacesContext.addPartialTarget(component);
//              }
//        }
        if (firstPageUIComp!=null) {
            adfFacesContext.addPartialTarget(firstPageUIComp);
        }
        if (previousPageUIComp!=null) {
            adfFacesContext.addPartialTarget(previousPageUIComp);
        }        
        if (nextPageUIComp!=null) {
            adfFacesContext.addPartialTarget(nextPageUIComp);
        }
        if (lastPageUIComp!=null) {
            adfFacesContext.addPartialTarget(lastPageUIComp);
        }
        if (rangeSizeUIComp!=null) {
            adfFacesContext.addPartialTarget(rangeSizeUIComp);
        }
        if (pageNoUIComp!=null) {
            adfFacesContext.addPartialTarget(pageNoUIComp);
        }
        if (lastPageNoUIComp!=null) {
            adfFacesContext.addPartialTarget(lastPageNoUIComp);
        }
        if (tableUIComp!=null) {
            adfFacesContext.addPartialTarget(tableUIComp);
        }
        
        BindingContext bindingCtx = BindingContext.getCurrent();
        BindingContainer bindings = bindingCtx.getCurrentBindingsEntry();
        
        OperationBinding ob = bindings.getOperationBinding(operationBinding);
        ob.getParamsMap().put("request", request);
        ob.execute();

        QueryResponse response = (QueryResponse)ob.getResult();
        if (response!=null && response.getTotalRowsCount()!=null) {
            setTotalRowsCount(response.getTotalRowsCount());
        }
        
        
        
    }
    
    // find a jsf component      
    private UIComponent getUIComponent(String name) {  
     FacesContext facesCtx = FacesContext.getCurrentInstance();  
     return facesCtx.getViewRoot().findComponent(name) ;  
    }  
    
    // find a UIComponent inside a UIComponent   
    private UIComponent getUIComponent(UIComponent component, String name) {  
            if (component != null)  
                System.out.println(component.getId());  
              
            List<UIComponent> items = component.getChildren();  
            Iterator<UIComponent> facets = component.getFacetsAndChildren();  
      
            if  ( items.size() > 0 ) {  
              System.out.println("got childern");  
              for (UIComponent item : items) {  
                  UIComponent found = getUIComponent(item, name);  
                  if (found != null) {  
                      return found;  
                  }  
                  if (item.getId()!=null && item.getId().equalsIgnoreCase(name)) {  
                      return item;  
                  }  
              }  
            } else if ( facets.hasNext()) {  
                System.out.println("got facets");  
                while ( facets.hasNext() ){  
                  UIComponent facet = facets.next();    
                  UIComponent found = getUIComponent(facet, name);  
                  if (found != null) {  
                      return found;  
                  }  
                  if (facet.getId()!=null && facet.getId().equalsIgnoreCase(name)) {  
                      return facet;  
                  }  
                }  
            }  
            return null;  
        } 
    
    public void setPageNumber(int pageNumber) {
      this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
      return pageNumber;
    }

//    public void setResults(List results) {
//        this.results = results;
//    }
//
//    public List getResults() {
//        return results;
//    }
//    
//    public List getResults(String operation) {
//        setOperationBinding(operation);
//        return results;
//    }

    public void setRangeSize(Integer rangeSize) {
        this.rangeSize = rangeSize;
    }

    public Integer getRangeSize() {
        if (rangeSize==null) {
            rangeSize=10;
        }
        return rangeSize;
    }


    public void setOperationBinding(String operationBinding) {
        this.operationBinding = operationBinding;
    }

    public String getOperationBinding() {
        return operationBinding;
    }
/*
    public void setRequest(T request) {
        this.request = request;
    }

    public T getRequest() {
        return request;
    }
*/
	  public void setRequest(QueryRequest request) {
        this.request = request;
    }

    public QueryRequest getRequest() {
        return request;
    }
	
    public void setTotalRowsCount(int totalRowsCount) {
        this.totalRowsCount = totalRowsCount;
    }

    public int getTotalRowsCount() {
        return totalRowsCount;
    }
    
    public void setFirstPageUIComp(RichCommandImageLink firstPageUIComp) {
        this.firstPageUIComp = firstPageUIComp;
    }

    public RichCommandImageLink getFirstPageUIComp() {
        return firstPageUIComp;
    }
    
    public void setRangeSizeUIComp(RichSelectOneChoice rangeSizeUIComp) {
        this.rangeSizeUIComp = rangeSizeUIComp;
    }

    public RichSelectOneChoice getRangeSizeUIComp() {
        return rangeSizeUIComp;
    }

    public void setPreviousPageUIComp(RichCommandImageLink previousPageUIComp) {
        this.previousPageUIComp = previousPageUIComp;
    }

    public RichCommandImageLink getPreviousPageUIComp() {
        return previousPageUIComp;
    }

    public void setNextPageUIComp(RichCommandImageLink nextPageUIComp) {
        this.nextPageUIComp = nextPageUIComp;
    }

    public RichCommandImageLink getNextPageUIComp() {
        return nextPageUIComp;
    }

    public void setLastPageUIComp(RichCommandImageLink lastPageUIComp) {
        this.lastPageUIComp = lastPageUIComp;
    }

    public RichCommandImageLink getLastPageUIComp() {
        return lastPageUIComp;
    }
    
    public void setPageNoUIComp(RichInputText pageNoUIComp) {
        this.pageNoUIComp = pageNoUIComp;
    }

    public RichInputText getPageNoUIComp() {
        return pageNoUIComp;
    }

    public void setLastPageNoUIComp(RichOutputText lastPageNoUIComp) {
        this.lastPageNoUIComp = lastPageNoUIComp;
    }

    public RichOutputText getLastPageNoUIComp() {
        return lastPageNoUIComp;
    }

    public void setTableUIComp(RichTable tableUIComp) {
        this.tableUIComp = tableUIComp;
    }

    public RichTable getTableUIComp() {
        return tableUIComp;
    }
}
