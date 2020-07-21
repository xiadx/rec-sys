class TFFeature(object):

    def __init__(self,
                 name,
                 alias,
                 column,
                 dtype,
                 shape,
                 default_value,
                 scope):
        self.name = name
        self.alias = alias
        self.column = column
        self.dtype = dtype
        self.shape = shape
        self.default_value = default_value
        self.scope = scope
