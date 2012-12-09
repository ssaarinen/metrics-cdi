package com.remion.cdi.metrics;

import static junit.framework.Assert.assertNotNull;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.remion.metrics.cdi.MetricsProducer;
import com.remion.metrics.cdi.MetricsRegistryWrapper;
import com.remion.metrics.cdi.annotation.ApplicationMetric;
import com.remion.metrics.cdi.annotation.ApplicationMetrics;
import com.yammer.metrics.core.Counter;
import com.yammer.metrics.core.Histogram;
import com.yammer.metrics.core.Meter;
import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.Timer;

@RunWith(Arquillian.class)
public class MetricsProducerTest {

	@Deployment
    public static Archive<?> getDeployment() {
        
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
        
        Archive<?> archive = ShrinkWrap.create(WebArchive.class, "metericsproducer-test.war")
                .addPackages(true, MetricsProducer.class.getPackage() )
                .addAsWebInfResource("beans.xml")
                .addAsLibraries(resolver.artifacts(
                        "org.apache.deltaspike.core:deltaspike-core-api", 
                        "org.apache.deltaspike.core:deltaspike-core-impl",
                        "com.yammer.metrics:metrics-core").resolveAsFiles());
        return archive;
    }
	
	@Inject @ApplicationMetrics
	MetricsRegistryWrapper metricsRegistry;
	
	@Inject @ApplicationMetric
	Timer timer;
	
	@Inject @ApplicationMetric(name="customName")
	Timer timer2;
	
	@Inject @ApplicationMetric
	Meter meter;
	
	@Inject @ApplicationMetric
	Counter counter;
	
	@Inject @ApplicationMetric
	Histogram histogram;
	
	@Test
	public void testTimerInjection() {
		assertNotNull(timer);
	}
	
	@Test
	public void testTimerWithCustomName() {
		assertNotNull(timer2);
		Timer fromReg =	(Timer) metricsRegistry.allMetrics().get(new MetricName(getClass(), "customName"));
		assertNotNull(fromReg);
	}
	
	@Test
	public void testMeterInjection() {
		assertNotNull(meter);
	}
	
	@Test
	public void testCounterInjection() {
		assertNotNull(counter);
	}
	
	@Test
	public void testHistogramInjection() {
		assertNotNull(histogram);
	}
	
}
