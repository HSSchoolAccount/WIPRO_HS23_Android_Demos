package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the `libs` extension.
*/
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final AndroidGradleLibraryAccessors laccForAndroidGradleLibraryAccessors = new AndroidGradleLibraryAccessors(owner);
    private final AndroidxLibraryAccessors laccForAndroidxLibraryAccessors = new AndroidxLibraryAccessors(owner);
    private final ComposeLibraryAccessors laccForComposeLibraryAccessors = new ComposeLibraryAccessors(owner);
    private final KotlinLibraryAccessors laccForKotlinLibraryAccessors = new KotlinLibraryAccessors(owner);
    private final ReactivexLibraryAccessors laccForReactivexLibraryAccessors = new ReactivexLibraryAccessors(owner);
    private final SquareupLibraryAccessors laccForSquareupLibraryAccessors = new SquareupLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

        /**
         * Creates a dependency provider for barcodescanner (me.dm7.barcodescanner:zxing)
         * This dependency was declared in catalog libraries.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getBarcodescanner() { return create("barcodescanner"); }

        /**
         * Creates a dependency provider for gson (com.google.code.gson:gson)
         * This dependency was declared in catalog libraries.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGson() { return create("gson"); }

        /**
         * Creates a dependency provider for junit (junit:junit)
         * This dependency was declared in catalog libraries.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJunit() { return create("junit"); }

    /**
     * Returns the group of libraries at androidGradle
     */
    public AndroidGradleLibraryAccessors getAndroidGradle() { return laccForAndroidGradleLibraryAccessors; }

    /**
     * Returns the group of libraries at androidx
     */
    public AndroidxLibraryAccessors getAndroidx() { return laccForAndroidxLibraryAccessors; }

    /**
     * Returns the group of libraries at compose
     */
    public ComposeLibraryAccessors getCompose() { return laccForComposeLibraryAccessors; }

    /**
     * Returns the group of libraries at kotlin
     */
    public KotlinLibraryAccessors getKotlin() { return laccForKotlinLibraryAccessors; }

    /**
     * Returns the group of libraries at reactivex
     */
    public ReactivexLibraryAccessors getReactivex() { return laccForReactivexLibraryAccessors; }

    /**
     * Returns the group of libraries at squareup
     */
    public SquareupLibraryAccessors getSquareup() { return laccForSquareupLibraryAccessors; }

    /**
     * Returns the group of versions at versions
     */
    public VersionAccessors getVersions() { return vaccForVersionAccessors; }

    /**
     * Returns the group of bundles at bundles
     */
    public BundleAccessors getBundles() { return baccForBundleAccessors; }

    /**
     * Returns the group of plugins at plugins
     */
    public PluginAccessors getPlugins() { return paccForPluginAccessors; }

    public static class AndroidGradleLibraryAccessors extends SubDependencyFactory {

        public AndroidGradleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for core (com.android.tools.build:gradle)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getCore() { return create("androidGradle.core"); }

    }

    public static class AndroidxLibraryAccessors extends SubDependencyFactory {

        public AndroidxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for datastore (androidx.datastore:datastore-preferences)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getDatastore() { return create("androidx.datastore"); }

            /**
             * Creates a dependency provider for fragment (androidx.fragment:fragment-ktx)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getFragment() { return create("androidx.fragment"); }

            /**
             * Creates a dependency provider for lifecycle (androidx.lifecycle:lifecycle-runtime-ktx)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getLifecycle() { return create("androidx.lifecycle"); }

    }

    public static class ComposeLibraryAccessors extends SubDependencyFactory {
        private final ComposeUiLibraryAccessors laccForComposeUiLibraryAccessors = new ComposeUiLibraryAccessors(owner);

        public ComposeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for activity (androidx.activity:activity-compose)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getActivity() { return create("compose.activity"); }

            /**
             * Creates a dependency provider for compiler (androidx.compose.compiler:compiler)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getCompiler() { return create("compose.compiler"); }

            /**
             * Creates a dependency provider for material (androidx.compose.material:material)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getMaterial() { return create("compose.material"); }

            /**
             * Creates a dependency provider for rxjava3 (androidx.compose.runtime:runtime-rxjava3)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getRxjava3() { return create("compose.rxjava3"); }

        /**
         * Returns the group of libraries at compose.ui
         */
        public ComposeUiLibraryAccessors getUi() { return laccForComposeUiLibraryAccessors; }

    }

    public static class ComposeUiLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {
        private final ComposeUiTestLibraryAccessors laccForComposeUiTestLibraryAccessors = new ComposeUiTestLibraryAccessors(owner);
        private final ComposeUiToolingLibraryAccessors laccForComposeUiToolingLibraryAccessors = new ComposeUiToolingLibraryAccessors(owner);

        public ComposeUiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ui (androidx.compose.ui:ui)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> asProvider() { return create("compose.ui"); }

        /**
         * Returns the group of libraries at compose.ui.test
         */
        public ComposeUiTestLibraryAccessors getTest() { return laccForComposeUiTestLibraryAccessors; }

        /**
         * Returns the group of libraries at compose.ui.tooling
         */
        public ComposeUiToolingLibraryAccessors getTooling() { return laccForComposeUiToolingLibraryAccessors; }

    }

    public static class ComposeUiTestLibraryAccessors extends SubDependencyFactory {

        public ComposeUiTestLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for junit4 (androidx.compose.ui:ui-test-junit4)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getJunit4() { return create("compose.ui.test.junit4"); }

    }

    public static class ComposeUiToolingLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public ComposeUiToolingLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for tooling (androidx.compose.ui:ui-tooling)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> asProvider() { return create("compose.ui.tooling"); }

            /**
             * Creates a dependency provider for preview (androidx.compose.ui:ui-tooling-preview)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getPreview() { return create("compose.ui.tooling.preview"); }

    }

    public static class KotlinLibraryAccessors extends SubDependencyFactory {

        public KotlinLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for gradle (org.jetbrains.kotlin:kotlin-gradle-plugin)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getGradle() { return create("kotlin.gradle"); }

    }

    public static class ReactivexLibraryAccessors extends SubDependencyFactory {

        public ReactivexLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for rxKotlin (io.reactivex.rxjava3:rxkotlin)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getRxKotlin() { return create("reactivex.rxKotlin"); }

    }

    public static class SquareupLibraryAccessors extends SubDependencyFactory {
        private final SquareupRetrofitLibraryAccessors laccForSquareupRetrofitLibraryAccessors = new SquareupRetrofitLibraryAccessors(owner);

        public SquareupLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for picasso (com.squareup.picasso:picasso)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getPicasso() { return create("squareup.picasso"); }

        /**
         * Returns the group of libraries at squareup.retrofit
         */
        public SquareupRetrofitLibraryAccessors getRetrofit() { return laccForSquareupRetrofitLibraryAccessors; }

    }

    public static class SquareupRetrofitLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {
        private final SquareupRetrofitOkhttp3LibraryAccessors laccForSquareupRetrofitOkhttp3LibraryAccessors = new SquareupRetrofitOkhttp3LibraryAccessors(owner);

        public SquareupRetrofitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for retrofit (com.squareup.retrofit2:retrofit)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> asProvider() { return create("squareup.retrofit"); }

            /**
             * Creates a dependency provider for gson (com.squareup.retrofit2:converter-gson)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getGson() { return create("squareup.retrofit.gson"); }

            /**
             * Creates a dependency provider for rxjava3 (com.squareup.retrofit2:adapter-rxjava3)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getRxjava3() { return create("squareup.retrofit.rxjava3"); }

        /**
         * Returns the group of libraries at squareup.retrofit.okhttp3
         */
        public SquareupRetrofitOkhttp3LibraryAccessors getOkhttp3() { return laccForSquareupRetrofitOkhttp3LibraryAccessors; }

    }

    public static class SquareupRetrofitOkhttp3LibraryAccessors extends SubDependencyFactory {

        public SquareupRetrofitOkhttp3LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for logging (com.squareup.okhttp3:logging-interceptor)
             * This dependency was declared in catalog libraries.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getLogging() { return create("squareup.retrofit.okhttp3.logging"); }

    }

    public static class VersionAccessors extends VersionFactory  {

        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: activityCompose (1.7.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getActivityCompose() { return getVersion("activityCompose"); }

            /**
             * Returns the version associated to this alias: androidGradlePlugin (8.1.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getAndroidGradlePlugin() { return getVersion("androidGradlePlugin"); }

            /**
             * Returns the version associated to this alias: barcodeScanner (1.9.8)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getBarcodeScanner() { return getVersion("barcodeScanner"); }

            /**
             * Returns the version associated to this alias: compileSdk (33)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getCompileSdk() { return getVersion("compileSdk"); }

            /**
             * Returns the version associated to this alias: composeCompiler (1.4.4)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getComposeCompiler() { return getVersion("composeCompiler"); }

            /**
             * Returns the version associated to this alias: composeMaterial (1.4.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getComposeMaterial() { return getVersion("composeMaterial"); }

            /**
             * Returns the version associated to this alias: composeRuntime (1.4.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getComposeRuntime() { return getVersion("composeRuntime"); }

            /**
             * Returns the version associated to this alias: composeUi (1.4.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getComposeUi() { return getVersion("composeUi"); }

            /**
             * Returns the version associated to this alias: datastorePreferences (1.0.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getDatastorePreferences() { return getVersion("datastorePreferences"); }

            /**
             * Returns the version associated to this alias: fragmentKtx (1.5.6)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getFragmentKtx() { return getVersion("fragmentKtx"); }

            /**
             * Returns the version associated to this alias: gson (2.9.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getGson() { return getVersion("gson"); }

            /**
             * Returns the version associated to this alias: junit (4.13.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getJunit() { return getVersion("junit"); }

            /**
             * Returns the version associated to this alias: jvmTarget (11)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getJvmTarget() { return getVersion("jvmTarget"); }

            /**
             * Returns the version associated to this alias: kotlin (1.8.10)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getKotlin() { return getVersion("kotlin"); }

            /**
             * Returns the version associated to this alias: lifecycleRuntimeKtx (2.6.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getLifecycleRuntimeKtx() { return getVersion("lifecycleRuntimeKtx"); }

            /**
             * Returns the version associated to this alias: minSdk (21)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getMinSdk() { return getVersion("minSdk"); }

            /**
             * Returns the version associated to this alias: okhttp3 (4.11.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getOkhttp3() { return getVersion("okhttp3"); }

            /**
             * Returns the version associated to this alias: picasso (2.5.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getPicasso() { return getVersion("picasso"); }

            /**
             * Returns the version associated to this alias: retrofit (2.9.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getRetrofit() { return getVersion("retrofit"); }

            /**
             * Returns the version associated to this alias: rxKotlin (3.0.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getRxKotlin() { return getVersion("rxKotlin"); }

            /**
             * Returns the version associated to this alias: targetSdk (33)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libraries.versions.toml
             */
            public Provider<String> getTargetSdk() { return getVersion("targetSdk"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}
