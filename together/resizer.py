# -*- coding: utf-8 -*-
import sys
from wand.image import Image

def main():
    source_path = sys.argv[1]
    output_path = sys.argv[2]

    with Image(filename=source_path) as img:
        for r in 1, 2, 3:
            with img.clone() as i:
                i.resize(int(i.width * r * 0.25), int(i.height * r * 0.25))
                resized_file_name = '{0}-{1}.jpg'.format(output_path, r)
                i.save(filename=resized_file_name)
                print resized_file_name


if __name__ == '__main__':
    main()