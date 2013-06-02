#IdCreator

If you're doing TestBench or JMeter testing in your application, you have to set sane, unique id's for your UI components. But in a dynamic UI this can be more difficult than it needs to be.

IdCreator helps in this by creating "component paths" that are automatically used as id's. The component paths are simple interfaces, so only the components you choose participate in building the full path for a single component.