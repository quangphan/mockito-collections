package uk.co.webamoeba.mockito.collections.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks;
import uk.co.webamoeba.mockito.collections.sample.MockReturnValuesOfCollectionOfMocksStory.MockitoCollectionsTest;
import uk.co.webamoeba.mockito.collections.sample.MockReturnValuesOfCollectionOfMocksStory.PlainMockitoTest;

/**
 * <b>As a</b> developer<br />
 * <b>I want</b> to inject a collection of collaborators into an object under test<br />
 * <b>So that</b> I can mock the return values of the collaborators<br />
 * <b>And</b> assert the behaviour of the object under test
 * 
 * @author James Kennard
 */
// FIXME this is a mishmash of a sample and a BDD acceptance test for mockito-collections-core
@RunWith(Suite.class)
@SuiteClasses({ PlainMockitoTest.class, MockitoCollectionsTest.class })
public class MockReturnValuesOfCollectionOfMocksStory {

	@RunWith(MockitoJUnitRunner.class)
	public static class PlainMockitoTest {

		@InjectMocks
		private SampleClassUnderTest sampleClassUnderTest;

		@Mock
		private SampleDataProvider sampleDataProvider1;

		@Mock
		private SampleDataProvider sampleDataProvider2;

		@Before
		public void before() {
			sampleClassUnderTest.setSampleDataProviders(Arrays.asList(sampleDataProvider1, sampleDataProvider2));
		}

		@Test
		public void shouldCallCollaboratorAndSampleListeners() {
			// Given
			SampleData sampleData1 = mock(SampleData.class);
			SampleData sampleData2 = mock(SampleData.class);
			given(sampleDataProvider1.getSampleData()).willReturn(sampleData1);
			given(sampleDataProvider2.getSampleData()).willReturn(sampleData2);

			// When
			List<SampleData> sampleData = sampleClassUnderTest.callSampleDataProvidersAndGetSampleData();

			// Then
			assertEquals(2, sampleData.size());
			assertSame(sampleData1, sampleData.get(0));
			assertSame(sampleData2, sampleData.get(1));
		}
	}

	@RunWith(MockitoJUnitRunner.class)
	public static class MockitoCollectionsTest {

		@InjectMocks
		private SampleClassUnderTest sampleClassUnderTest;

		@Mock
		private SampleDataProvider sampleDataProvider1;

		@Mock
		private SampleDataProvider sampleDataProvider2;

		@Before
		public void before() {
			MockitoCollections.initialise(this);
		}

		@Test
		public void shouldCallCollaboratorAndSampleListeners() {
			// Given
			SampleData sampleData1 = mock(SampleData.class);
			SampleData sampleData2 = mock(SampleData.class);
			given(sampleDataProvider1.getSampleData()).willReturn(sampleData1);
			given(sampleDataProvider2.getSampleData()).willReturn(sampleData2);

			// When
			List<SampleData> sampleData = sampleClassUnderTest.callSampleDataProvidersAndGetSampleData();

			// Then
			assertEquals(2, sampleData.size());
			assertSame(sampleData1, sampleData.get(0));
			assertSame(sampleData2, sampleData.get(1));
		}
	}

	@RunWith(MockitoJUnitRunner.class)
	public static class MockitoCollectionsGivenCollectionOfMocksAnnotation {

		@InjectMocks
		private SampleClassUnderTest sampleClassUnderTest;

		@CollectionOfMocks(numberOfMocks = 2)
		private List<SampleDataProvider> sampleDataProviders;

		@Before
		public void before() {
			MockitoCollections.initialise(this);
		}

		@Test
		public void shouldCallCollaboratorAndSampleListeners() {
			// Given
			SampleData sampleData1 = mock(SampleData.class);
			SampleData sampleData2 = mock(SampleData.class);
			given(sampleDataProviders.get(0).getSampleData()).willReturn(sampleData1);
			given(sampleDataProviders.get(1).getSampleData()).willReturn(sampleData2);

			// When
			List<SampleData> sampleData = sampleClassUnderTest.callSampleDataProvidersAndGetSampleData();

			// Then
			assertEquals(2, sampleData.size());
			assertSame(sampleData1, sampleData.get(0));
			assertSame(sampleData2, sampleData.get(1));
		}
	}
}
