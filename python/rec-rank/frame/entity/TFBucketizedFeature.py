class TFBucketizedFeature(object):

    def __init__(self,
                 name,
                 boundaries,
                 scope):
        self.name = name
        self.boundaries = boundaries
        self.scope = scope
