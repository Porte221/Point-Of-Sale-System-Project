package com.assignment.seis602.test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.assignment.seis602.item.Item;
import com.assignment.seis602.item.ItemCategory;

public class ItemTest
{
	
	protected Item item1,item2;
	ArrayList<Item> itemList;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception 
	{
		item1 =  new Item("ABCD",ItemCategory.Electronics,10);
		item2 =  new Item("XYZS",ItemCategory.Grocery,20);
		itemList = new ArrayList<Item>();
	
	}

	@After
	public void tearDown() throws Exception 
	{
		
	}

	@Test
	public void testFailureGetItemList() 
	{
       assertEquals("object found to be of arrayList class", Map.class, Item.getItemList().getClass());
	}
	
	@Test
	public void testSuccessGetItemList() 
	{
       assertEquals("object found to be of arrayList class", ArrayList.class, Item.getItemList().getClass());
	}

	@Test
	public void testFailureItemCreation()
	{
		assertEquals(ItemCategory.class,item1.getClass());
		
	}
	
	@Test
	public void testSuccessItemCreation()
	{
		assertEquals(Item.class,item1.getClass());
		
	}
	
	@Test
	public void testFailureGetItemName() 
	{
		assertTrue(item1.getItemName().equals("ABCED"));
		
	}
	
	@Test
	public void testSuccessGetItemName() 
	{
		assertTrue(item1.getItemName().equals("ABCD"));
		
	}

	@Test
	public void testFailureSetItemName() 
	{
	  item1.setItemName("eeee");
	  assertEquals(item1.getItemName(),"eee");
		
	}
	
	@Test
	public void testSuccessSetItemName() 
	{
	  item1.setItemName("max");
	  assertEquals(item1.getItemName(),"max");
		
	}

}
