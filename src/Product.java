
public class Product implements java.io.Serializable { // ����ȭ ���� Ŭ���� �����

	private static final long serialVersionUID = 1L;
	private String productName; // ��ǰ �̸�
	private String productCode; // ��ǰ �ڵ�
	private int productNumber; // ��ǰ ����
	private int price; // ��ǰ ����
	
	// �μ� �ִ� ������ 1 
	Product(String productName, String productCode, int productNumber, int price)
	{
		this.productName = productName;
		this.productCode = productCode;
		this.productNumber = productNumber;
		this.price = price;
	}
	
	// �μ� ���� �� ������ 2
	Product() {}
	
	// equals �Լ� ������
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Product)) // Product�� �ƴ� ��ü��� �� �Ұ�
			return false;
		Product p = (Product) obj;
		return p.productCode.equals(this.productCode); // ã�� �ִ� ��ǰ�ڵ�� ������ Ȯ��
	}
	
	// ��ǰ �̸� ��ȯ
	public String getName()
	{
		return productName;
	}
	
	// ��ǰ �ڵ� ��ȯ
	public String getCode()
	{
		return productCode;
	}
		
	//��ǰ �ڵ� ����
	public void setCode(String pCode) 
	{
		productCode = pCode;
	}
	// ��ǰ ���� ��ȯ
	public int getNumber()
	{
		return productNumber;
	}
	
	// ��ǰ ���� �߰�
	public void addNumber()
	{
		productNumber++;
	}
	
	// �ܰ� �߰�
	public void addNumber(int n)
	{
		productNumber += n;
	}
	
	// ��ǰ �뿩 �������� Ȯ�� �� ��ǰ ���� 1�� ����
	public void subNumber() throws Exception{
		if(productNumber < 1) // ��� ������ 1���� ���� ��� 
			throw new Exception("��� �����մϴ�."); // �ͼ��� �߻�
		else // ��� ���� ���ڰ� 1�̻��� ���
			productNumber--; // ��� �� 1�� ����
	}
	
	// ��ǰ �뿩 �������� Ȯ�� �� ��ǰ ���� n�� ���� // ��Ʈ�� ������ �� ���ش�..........
	public void subNumber(int n) throws Exception {
		if(productNumber - n < 0) // n�� ��ŭ �����ϸ� 0���� �۰Եɶ� 
			throw new Exception("��� �����մϴ�."); // �ͼ��� �߻�
		else 
			productNumber -= n; // ��� �� �߰�
	}	
	
	// ��ǰ ���� ��ȯ
	public int getPrice()
	{
		return price;
	}
	
	// ��� �˻� �Լ� (��� ������ true, �ƴϸ� false ��ȯ)
	public boolean isEmpty()
	{
		if(productNumber > 0)
			return true;
		else
			return false;
	}
}