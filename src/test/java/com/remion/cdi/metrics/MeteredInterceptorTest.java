package com.remion.cdi.metrics;

import static junit.framework.Assert.assertEquals;
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

import com.remion.metrics.cdi.MeteredInterceptor;
import com.remion.metrics.cdi.MetricsRegistryWrapper;
import com.remion.metrics.cdi.annotation.ApplicationMetrics;
import com.remion.metrics.cdi.annotation.Metered;
import com.yammer.metrics.core.Meter;
import com.yammer.metrics.core.Metric;
import com.yammer.metrics.core.MetricName;

@RunWith(Arquillian.class)
public class MeteredInterceptorTest {
	
	@Deployment
    public static Archive<?> getDeployment() {
        
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
        
        Archive<?> archive = ShrinkWrap.create(WebArchive.class, "metered-test.war")
                .addPackages(true, MeteredInterceptor.class.getPackage() )
                .addAsWebInfResource("beans.xml")
                .addAsLibraries(resolver.artifacts(
                        "org.apache.deltaspike.core:deltaspike-core-api", 
                        "org.apache.deltaspike.core:deltaspike-core-impl",
                        "com.yammer.metrics:metrics-core").resolveAsFiles());
        return archive;
    }
	
	@Inject @ApplicationMetrics
	MetricsRegistryWrapper registry;
	
	@Inject
	MeteredTestObject testObject;
	
	@Test
	public void test() throws Exception {
		testObject.getValue();
		
		Metric metric = registry.allMetrics().get(new MetricName(MeteredTestObject.class, "getValue"));
		assertEquals(1, registry.allMetrics().size());
		assertNotNull(metric);
		assertEquals(1, ((Meter) metric).count());
	}
	
	
	public static class MeteredTestObject {
		
		@Metered
		public int getValue() {
			return 10;
		}
	}
}
