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

import com.remion.metrics.cdi.MetricsRegistryWrapper;
import com.remion.metrics.cdi.TimedInterceptor;
import com.remion.metrics.cdi.annotation.ApplicationMetrics;
import com.remion.metrics.cdi.annotation.Timed;
import com.yammer.metrics.core.Metric;
import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.Timer;

@RunWith(Arquillian.class)
public class TimedInterceptorTest {

	
	@Deployment
    public static Archive<?> getDeployment() {
        
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
        
        Archive<?> archive = ShrinkWrap.create(WebArchive.class, "timed-test.war")
                .addPackages(true, TimedInterceptor.class.getPackage() )
//                .addClass(TestableParameterLog.class)
                .addAsWebInfResource("beans.xml")
//                .addAsServiceProvider(Extension.class, ParameterLoggerExtension.class)
                .addAsLibraries(resolver.artifacts(
                        "org.apache.deltaspike.core:deltaspike-core-api", 
                        "org.apache.deltaspike.core:deltaspike-core-impl",
                        "com.yammer.metrics:metrics-core").resolveAsFiles());
        return archive;
    }
	
	@Inject TimedTestObject testObject;

	@Inject @ApplicationMetrics
	MetricsRegistryWrapper registry;
	
	@Test
	public void test() throws Exception {
		testObject.doIt();
		
		Metric metric = registry.allMetrics().get(new MetricName(TimedTestObject.class, "doIt"));
		assertEquals(1, registry.allMetrics().size());
		assertNotNull(metric);
		assertEquals(1, ((Timer) metric).getSnapshot().size());
	}
	
	
	
	public static class TimedTestObject {
		
		@Timed
		public void doIt() throws Exception {
			Thread.sleep(1);
			
		
		}
		
	}
   
}
